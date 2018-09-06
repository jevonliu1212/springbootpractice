package com.springboot.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.Validator;
import org.springframework.batch.support.DatabaseType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.springboot.batch.CsvBeanValidator;
import com.springboot.batch.CsvItemProcessor;
import com.springboot.batch.CsvItemProcessor1;
import com.springboot.batch.CsvJobListener;
import com.springboot.entity.Account;
import com.springboot.entity.Employee;

@Configuration
@EnableBatchProcessing
public class CsvBatchConfig {

	@Bean
    public ItemReader<Account> reader(){
		System.out.println("reader....");
        // 使用FlatFileItemReader 读取文件
        FlatFileItemReader<Account> reader = new FlatFileItemReader<Account>();
        reader.setResource(new ClassPathResource("people.csv"));

        reader.setLineMapper(new DefaultLineMapper<Account>(){{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setNames(new String[]{"name","money"});
            }}); 
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Account>(){{
                setTargetType(Account.class);
            }});
        }});

        return reader;
    }
	
	@Bean
    public ItemReader<Employee> reader1(){
		System.out.println("reader....");
        // 使用FlatFileItemReader 读取文件
        FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>();
        reader.setResource(new ClassPathResource("people.csv"));

        reader.setLineMapper(new DefaultLineMapper<Employee>(){{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setNames(new String[]{"name","mobile"});
            }}); 
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>(){{
                setTargetType(Employee.class);
            }});
        }});

        return reader;
    }
	
	@Bean
    public ItemProcessor<Account, Account> processor(){
        CsvItemProcessor processor = new CsvItemProcessor();
        processor.setValidator(csvBeanValidator());
        return processor;
    }
	
	@Bean
    public ItemProcessor<Employee, Employee> processor1(){
        CsvItemProcessor1 processor = new CsvItemProcessor1();
        processor.setValidator(csvBeanValidator1());
        return processor;
    }
	
	
	@Bean
    public Validator<Account> csvBeanValidator(){
        return new CsvBeanValidator<Account>();
    }
	
	@Bean
    public Validator<Employee> csvBeanValidator1(){
        return new CsvBeanValidator<Employee>();
    }
	
	@Bean
    public ItemWriter<Account> writer(DataSource dataSource){
        JdbcBatchItemWriter<Account> writer = new JdbcBatchItemWriter<Account>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Account>());
        String sql = "insert into account(name, remark,money) "
                                    + "values (:name, :remark,:money)";

        writer.setSql(sql);
        writer.setDataSource(dataSource);
        return writer;
    }
	
	@Bean
    public ItemWriter<Employee> writer1(DataSource dataSource){
        JdbcBatchItemWriter<Employee> writer = new JdbcBatchItemWriter<Employee>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
        String sql = "insert into employee(name,mobile) "
                                    + "values (:name,:mobile)";

        writer.setSql(sql);
        writer.setDataSource(dataSource);
        return writer;
    }
	
	/**
     * 作业仓库
     * 
     * @param dataSource
     * @param transactionManager
     * @return
     * @throws Exception
     */
    @Bean
    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception{

        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDatabaseType(DatabaseType.MYSQL.name());

        return jobRepositoryFactoryBean.getObject();
    }

    /**
     * 作业调度器
     * 
     * @param dataSource
     * @param transactionManager
     * @return
     * @throws Exception
     */
    public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception{

        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(this.jobRepository(dataSource, transactionManager));

        return jobLauncher;
    }


    @Bean
    public Job importJob(JobBuilderFactory jobs, Step personStep,Step personStep1){
        return jobs.get("importJob")
                .incrementer(new RunIdIncrementer())
                .flow(personStep)
                .next(personStep1)// 为Job指定Step
                .end()
                .listener(csvJobListener()) // 绑定监听器
                .build();
    }

    @Bean
    public Step personStep(StepBuilderFactory stepBuilderFactory, 
                                ItemReader<Account> reader, ItemWriter<Account> writer, ItemProcessor<Account, Account> processor){
        return stepBuilderFactory.get("personStep")
                .<Account, Account>chunk(5000) // 批处理每次提交5000条数据
                .reader(reader) // 给step绑定reader
                .processor(processor) // 给step绑定processor
                .writer(writer) // 给step绑定writer
                .build();
    }
    
    @Bean
    public Step personStep1(StepBuilderFactory stepBuilderFactory, 
                                ItemReader<Employee> reader, ItemWriter<Employee> writer, ItemProcessor<Employee, Employee> processor){
        return stepBuilderFactory.get("personStep1")
                .<Employee, Employee>chunk(5000) // 批处理每次提交5000条数据
                .reader(reader) // 给step绑定reader
                .processor(processor) // 给step绑定processor
                .writer(writer) // 给step绑定writer
                .build();
    }

    @Bean
    public CsvJobListener csvJobListener(){
        return new CsvJobListener();
    }
}

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springboot.entity.Account;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Bean
    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager)
    throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDatabaseType("mysql");
        return jobRepositoryFactoryBean.getObject();
    }
    @Bean
    public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager)
    throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository(dataSource, transactionManager));
        return jobLauncher;
    }
    @Bean
    public Job importJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get("importJob")
        .incrementer(new RunIdIncrementer())
        .flow(s1)
        .end()
        .build();
    }
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Account> reader, ItemWriter<Account> writer,
    ItemProcessor<Account,Account> processor) {
        return stepBuilderFactory
        .get("step1")
        .<Account, Account>chunk(65000)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
    }
//    @Bean
//    public ItemReader<Account> reader() throws Exception {
//        //新建ItemReader接口的实现类返回
//        return reader;
//    }
//    @Bean
//    public ItemProcessor<Person, Person> processor() {
//        //新建ItemProcessor接口的实现类返回
//        return processor;
//    }
//    @Bean
//    public ItemWriter<Person> writer(DataSource dataSource) {
//        //新建ItemWriter接口的实现类返回
//        return writer;
//    }
}

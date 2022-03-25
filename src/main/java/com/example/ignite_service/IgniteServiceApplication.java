package com.example.ignite_service;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.*;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.Arrays;
import java.util.Collections;


@EnableIgniteRepositories(basePackages = "com.example.ignite_service.repository", includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RepositoryConfig.class))
@SpringBootApplication
public class IgniteServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(IgniteServiceApplication.class, args);


    }
    @Bean
    public Ignite igniteInstance() {
        IgniteConfiguration cfg = new IgniteConfiguration()
                // Enabling peer-class loading feature.
                .setPeerClassLoadingEnabled(true)
                .setDeploymentMode(DeploymentMode.CONTINUOUS)
                .setClientMode(true);

        // Ignite persistence configuration.
/*        DataStorageConfiguration storageCfg = new DataStorageConfiguration()
                .setWalMode(WALMode.LOG_ONLY)
                .setStoragePath("C:\\apache-ignite-2.12.0-bin\\work")
                .setWalPath("C:\\apache-ignite-2.12.0-bin\\work" + "/wal")
                .setWalArchivePath("C:\\apache-ignite-2.12.0-bin\\work" + "/wal/archive");

        // Enabling the persistence.
        storageCfg.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);

        // Applying settings.
        igniteConfiguration.setDataStorageConfiguration(storageCfg);*/

        // Discovery SPI

            TcpDiscoverySpi spi = new TcpDiscoverySpi();

            TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();

            // Set initial IP addresses.
            // Note that you can optionally specify a port or a port range.
            ipFinder.setAddresses(Arrays.asList("localhost", "localhost:47500..47509"));

            spi.setIpFinder(ipFinder);

            // Override default discovery SPI.
            cfg.setDiscoverySpi(spi);


        Ignite ignite = Ignition.start(cfg);

        // Cache configuration
        CacheConfiguration<Long, UserModel> user = new CacheConfiguration<Long, UserModel>("User")
                .setCacheMode(CacheMode.PARTITIONED)
                //.setBackups(1)
                .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC)
                .setIndexedTypes(Long.class, UserModel.class);

        ignite.getOrCreateCaches(Arrays.asList(user));

        return ignite;
    }

}

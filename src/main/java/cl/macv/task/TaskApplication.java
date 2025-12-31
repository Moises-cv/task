package cl.macv.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class TaskApplication implements CommandLineRunner {

	private final BuildProperties buildProperties;

	public TaskApplication(BuildProperties buildProperties) {
		this.buildProperties = buildProperties;
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

	@PostConstruct
	public void init() {
        log.info("!------------------------------------------------------------!");
        log.info("Build Name: {}", buildProperties.getName());
        log.info("Application Version: {}", buildProperties.getVersion());
        log.info("Build Group: {}", buildProperties.getGroup());
        log.info("Build Artifact: {}", buildProperties.getArtifact());
        log.info("Build Time: {}", buildProperties.getTime());
        log.info("!------------------------------------------------------------!");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Iniciando aplicación");
	}

}

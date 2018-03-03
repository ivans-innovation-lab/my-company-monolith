package com.idugalic.configuration;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.idugalic.queryside.blog.domain.BlogPost;
import com.idugalic.queryside.project.domain.Project;
import com.idugalic.queryside.team.domain.Member;
import com.idugalic.queryside.team.domain.Team;
import com.idugalic.security.domain.User;

/**
 * A configuration of rest data respositories for {@link BlogPost} and
 * {@link Project}.
 * 
 * @author idugalic
 *
 */
@Configuration
public class RestConfiguration extends RepositoryRestMvcConfiguration {

	public RestConfiguration(ApplicationContext context, ObjectFactory<ConversionService> conversionService) {
		super(context, conversionService);
	}

	@Configuration
	static class RestConfigurationExposeId extends RepositoryRestConfigurerAdapter {
		@Override
		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
			config.exposeIdsFor(BlogPost.class, Project.class, Team.class, Member.class, User.class);
			config.setBasePath("/api");
		}
	}

}

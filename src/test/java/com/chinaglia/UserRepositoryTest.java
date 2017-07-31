package com.chinaglia;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.chinaglia.model.User;
import com.chinaglia.repository.UserRepository;

	@RunWith(SpringRunner.class)
	@DataJpaTest
	public class UserRepositoryTest {
			
			@Autowired
			private TestEntityManager testEntityManager;
			
			@Autowired
			private UserRepository userRepo;
			
			@Test
			public void findByEmailAndCompareUser(){
				User frank = new User("Frank", "Sinatra", "ididit@my.way", "password");
				testEntityManager.persist(frank);
				testEntityManager.flush();
			
			
			User myUser = userRepo.findByEmail(frank.getEmail());
			
			assertThat(myUser.getEmail()).isEqualTo(frank.getEmail());
			}
	}



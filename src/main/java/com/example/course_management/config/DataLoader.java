package com.example.course_management.config;

import com.example.course_management.model.Course;
import com.example.course_management.model.HostUniversity;
import com.example.course_management.model.User;
import com.example.course_management.repository.CourseRepository;
import com.example.course_management.repository.HostUniversityRepository;
import com.example.course_management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Αρχικοποίηση Test Data για την εφαρμογή
 */
@Configuration
public class DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                    HostUniversityRepository hostUniversityRepository,
                                    CourseRepository courseRepository) {
        return args -> {
            logger.info("🔄 Checking if test data needs to be loaded...");

            // Έλεγχος αν υπάρχουν ήδη δεδομένα
            if (userRepository.count() > 0) {
                logger.info("✓ Test data already exists. Skipping initialization.");
                return;
            }

            logger.info("📊 Loading test data...");

            // 1. Δημιουργία Test Users
            User hostUser = new User();
            hostUser.setUsername("host");
            hostUser.setPassword("host123");
            hostUser.setUsertype("host_university");
            hostUser.setEmail("host@example.com");
            hostUser.setFullName("Test Host Representative");
            hostUser = userRepository.save(hostUser);
            logger.info("  ✓ Created user: {}", hostUser.getUsername());

            User testHostUser = new User();
            testHostUser.setUsername("testhost");
            testHostUser.setPassword("test123");
            testHostUser.setUsertype("host_university");
            testHostUser.setEmail("testhost@example.com");
            testHostUser.setFullName("Another Host University");
            testHostUser = userRepository.save(testHostUser);
            logger.info("  ✓ Created user: {}", testHostUser.getUsername());

            // 2. Δημιουργία Host Universities
            HostUniversity hostUniv = new HostUniversity();
            hostUniv.setUserId(hostUser.getUserId());
            hostUniv.setUniversityName("Athens University of Economics and Business");
            hostUniv.setCountry("Greece");
            hostUniv.setCity("Athens");
            hostUniv.setContactEmail("contact@aueb.gr");
            hostUniv.setContactPhone("+30 210 8203000");
            hostUniv = hostUniversityRepository.save(hostUniv);
            logger.info("  ✓ Created host university: {}", hostUniv.getUniversityName());

            HostUniversity testHostUniv = new HostUniversity();
            testHostUniv.setUserId(testHostUser.getUserId());
            testHostUniv.setUniversityName("Technical University of Munich");
            testHostUniv.setCountry("Germany");
            testHostUniv.setCity("Munich");
            testHostUniv.setContactEmail("contact@tum.de");
            testHostUniv.setContactPhone("+49 89 28901");
            testHostUniv = hostUniversityRepository.save(testHostUniv);
            logger.info("  ✓ Created host university: {}", testHostUniv.getUniversityName());

            // 3. Δημιουργία Sample Courses
            String[] courseData = {
                    "Computer Science|Advanced Algorithms|CS301|6|Fall",
                    "Computer Science|Database Systems|CS302|5|Spring",
                    "Mathematics|Linear Algebra|MATH201|6|Fall",
                    "Economics|Microeconomics|ECON101|5|Spring",
                    "Business|Project Management|BUS250|4|Fall"
            };

            for (String data : courseData) {
                String[] parts = data.split("\\|");
                Course course = new Course();
                course.setHostId(hostUniv.getHostId());
                course.setDept(parts[0]);
                course.setCourseName(parts[1]);
                course.setCode(parts[2]);
                course.setEcts(Integer.parseInt(parts[3]));
                course.setSemester(parts[4]);
                courseRepository.save(course);
                logger.info("  ✓ Created course: {} ({})", course.getCourseName(), course.getCode());
            }

            logger.info("✅ Test data loaded successfully!");
            logger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            logger.info("🎓 Login Credentials:");
            logger.info("   Username: host");
            logger.info("   Password: host123");
            logger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        };
    }
}


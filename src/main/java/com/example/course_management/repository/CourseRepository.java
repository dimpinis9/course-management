package com.example.course_management.repository;

import com.example.course_management.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    /**
     * Βρίσκει όλα τα μαθήματα ενός συγκεκριμένου Host University
     */
    List<Course> findByHostId(Integer hostId);

    /**
     * Βρίσκει όλα τα μαθήματα ενός συγκεκριμένου τμήματος
     */
    List<Course> findByDept(String dept);

    /**
     * Βρίσκει μαθήματα με συγκεκριμένο εξάμηνο
     */
    List<Course> findBySemester(String semester);

    /**
     * Βρίσκει μαθήματα με συγκεκριμένο κωδικό
     */
    Optional<Course> findByCode(String code);

    /**
     * Βρίσκει μαθήματα για συγκεκριμένο host και department
     */
    List<Course> findByHostIdAndDept(Integer hostId, String dept);

    /**
     * Διαγράφει μάθημα μόνο αν ανήκει στον συγκεκριμένο host
     */
    @Modifying
    @Query("DELETE FROM Course c WHERE c.courseId = :courseId AND c.hostId = :hostId")
    int deleteByCourseIdAndHostId(@Param("courseId") Integer courseId, @Param("hostId") Integer hostId);

    /**
     * Ελέγχει αν υπάρχει μάθημα με το συγκεκριμένο code για τον host
     */
    boolean existsByCodeAndHostId(String code, Integer hostId);

    /**
     * Μετράει πόσα μαθήματα έχει ένας host
     */
    long countByHostId(Integer hostId);
}


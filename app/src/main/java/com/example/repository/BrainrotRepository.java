package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.BrainrotImage;

public interface BrainrotRepository extends JpaRepository<BrainrotImage, Long> {

}

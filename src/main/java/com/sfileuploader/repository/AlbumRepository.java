package com.sfileuploader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sfileuploader.entity.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

}

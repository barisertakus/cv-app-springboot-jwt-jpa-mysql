package com.bariser.cvapp.service;

import com.bariser.cvapp.dto.CvDTO;

import java.util.List;

public interface CvService {

     List<CvDTO> getAll();

     CvDTO findByUserId(Long userId);

     CvDTO createCv(CvDTO cvDTO, Long userId);

     CvDTO editCv(CvDTO cvDTO, Long cvId);

     Boolean deleteCv(Long cvId);

}

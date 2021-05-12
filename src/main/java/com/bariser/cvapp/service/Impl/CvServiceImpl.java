package com.bariser.cvapp.service.Impl;

import com.bariser.cvapp.dto.CvDTO;
import com.bariser.cvapp.dto.UserDTO;
import com.bariser.cvapp.model.Cv;
import com.bariser.cvapp.model.User;
import com.bariser.cvapp.repository.CvRepository;
import com.bariser.cvapp.service.CvService;
import com.bariser.cvapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CvServiceImpl implements CvService {
    private final CvRepository cvRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    public CvServiceImpl(CvRepository cvRepository, UserService userService, ModelMapper modelMapper) {
        this.cvRepository = cvRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CvDTO> getAll(){
        List<Cv> cvs = cvRepository.findAll();
        List<CvDTO> cvDTOS = cvs.stream().map(cv ->
                modelMapper.map(cv, CvDTO.class))
                .collect(Collectors.toList());
        return cvDTOS;
    }

    @Override
    public CvDTO findByUserId(Long userId){
        UserDTO userDTO = userService.findById(userId);
        Cv cv = cvRepository.findCvByUserId(userDTO.getId());
        if(cv != null)
            return modelMapper.map(cv,CvDTO.class);
        return null;
    }

    @Override
    public CvDTO createCv(CvDTO cvDTO, Long userId) {
        UserDTO userDTO = userService.findById(userId);
        Cv cv = modelMapper.map(cvDTO, Cv.class);
        User user = modelMapper.map(userDTO, User.class);
        cv.setUser(user);

        return modelMapper.map(cvRepository.save(cv),CvDTO.class);
    }

    @Override
    public CvDTO editCv(CvDTO cvDTO, Long cvId) {
        Optional<Cv> cvOptional = cvRepository.findById(cvId);
        if(cvOptional.isPresent()){
            Cv cvNew = cvOptional.get();
            Cv cv = modelMapper.map(cvDTO,Cv.class);
            cvNew.setName(cv.getName());
            cvNew.setSurname(cv.getSurname());
            cvNew.setGender(cv.getGender());
            cvNew.setPhone(cv.getPhone());
            cvNew.setBirthDate(cv.getBirthDate());

            return modelMapper.map(cvRepository.save(cvNew), CvDTO.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCv(Long cvId) {
        cvRepository.deleteById(cvId);
        return true;
    }
}

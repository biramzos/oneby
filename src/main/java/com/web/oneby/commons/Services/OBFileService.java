package com.web.oneby.commons.Services;

import com.web.oneby.commons.DTOs.FileObject;
import com.web.oneby.commons.Models.OBFile;
import com.web.oneby.commons.Repositories.OBFileRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OBFileService {

    private OBFileRepository fileRepository;

    @Autowired
    public OBFileService (OBFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public OBFile getFileById(Long id) {
        return fileRepository.getOBFileById(id).orElseThrow(() -> new ObjectNotFoundException(OBFile.class, "File[" + id + "] not found!"));
    }

    public OBFile saveFile(FileObject fileObject) {
        return fileRepository.save(new OBFile(fileObject.getFile()));
    }

}

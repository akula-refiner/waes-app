package com.waes.assignment.waesapp.repo;

import com.waes.assignment.waesapp.model.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepo extends CrudRepository<Attachment, Long> {
}

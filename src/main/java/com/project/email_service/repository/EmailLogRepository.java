package com.project.email_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.email_service.entity.EmailLog;
public interface EmailLogRepository extends JpaRepository<EmailLog,Integer> {

}

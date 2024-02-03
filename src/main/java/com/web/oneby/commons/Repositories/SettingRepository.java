package com.web.oneby.commons.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import com.web.oneby.commons.Models.Setting;

@Repository
@Transactional
@EnableJpaRepositories
public interface SettingRepository extends JpaRepository<Setting, Long> {
}

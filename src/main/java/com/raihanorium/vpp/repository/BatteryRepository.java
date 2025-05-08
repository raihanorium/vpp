package com.raihanorium.vpp.repository;

import com.raihanorium.vpp.persistence.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {
}

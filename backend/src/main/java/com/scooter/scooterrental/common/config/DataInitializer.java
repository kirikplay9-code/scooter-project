package com.scooter.scooterrental.common.config;

import com.scooter.scooterrental.scooter.enums.entity.Scooter;
import com.scooter.scooterrental.scooter.enums.ScooterStatus;
import com.scooter.scooterrental.scooter.enums.repository.ScooterRepository;
import com.scooter.scooterrental.tariff.entity.Tariff;
import com.scooter.scooterrental.tariff.entity.repository.TariffRepository;
import com.scooter.scooterrental.user.enums.entity.Role;
import com.scooter.scooterrental.user.enums.UserRole;
import com.scooter.scooterrental.user.enums.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final TariffRepository tariffRepository;
    private final ScooterRepository scooterRepository;

    @Override
    public void run(String... args) {

        if (roleRepository.count() == 0) {
            Role client = new Role();
            client.setName(UserRole.CLIENT);
            Role admin = new Role();
            admin.setName(UserRole.ADMIN);
            roleRepository.save(client);
            roleRepository.save(admin);
        }

        if (tariffRepository.count() == 0) {
            Tariff tariff = new Tariff();
            tariff.setName("Стандарт");
            tariff.setPricePerMinute(5.0);
            tariff.setStartPrice(20.0);
            tariffRepository.save(tariff);
        }

        if (scooterRepository.count() == 0) {
            for (int i = 1; i <= 5; i++) {
                Scooter s = new Scooter();
                s.setSerialNumber("SCOOT-" + i);
                s.setModel("Xiaomi M365");
                s.setLatitude(55.751244 + (i * 0.001));
                s.setLongitude(37.618423 + (i * 0.001));
                s.setBatteryLevel(80 + i);
                s.setStatus(ScooterStatus.AVAILABLE);
                scooterRepository.save(s);
            }
        }
    }
}
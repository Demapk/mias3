package ru.kpfu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.domain.Appointment;
import ru.kpfu.domain.Patient;
import ru.kpfu.repository.AppointmentRepository;
import ru.kpfu.repository.PatientRepository;
import ru.kpfu.repository.UserRepository;
import ru.kpfu.security.SecurityUtils;
import ru.kpfu.service.dto.AppointmentDTO;
import ru.kpfu.service.mapper.AppointmentMapper;
import ru.kpfu.web.rest.errors.InternalServerErrorException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Appointment.
 */
@Service
@Transactional
public class AppointmentService {

    private final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;

    private final UserRepository userRepository;

    private final PatientRepository patientRepository;

    private final AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository, PatientRepository patientRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.appointmentMapper = appointmentMapper;
    }

    /**
     * Save a appointment.
     *
     * @param appointmentDTO the entity to save
     * @return the persisted entity
     */
    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        log.debug("Request to save Appointment : {}", appointmentDTO);
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(appointment);
    }

    /**
     * Get all the appointments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAll() {
        log.debug("Request to get all Appointments");
        return appointmentRepository.findAll().stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one appointment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AppointmentDTO findOne(Long id) {
        log.debug("Request to get Appointment : {}", id);
        Appointment appointment = appointmentRepository.findOne(id);
        return appointmentMapper.toDto(appointment);
    }

    /**
     * Delete the appointment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Appointment : {}", id);
        appointmentRepository.delete(id);
    }

    public AppointmentDTO makeAppointment(Long appointmentId) {
        String userLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<Patient> patient = patientRepository.findOneByUserLogin(userLogin);
        if (!patient.isPresent()) {
            throw new InternalServerErrorException("User could not be found");
        }
        Appointment appointment = appointmentRepository.findOne(appointmentId);

        return null;
    }

}

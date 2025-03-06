package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.builders.deviceBuilder;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.ClientRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, ClientRepository clientRepository) {
        this.deviceRepository = deviceRepository;
        this.clientRepository = clientRepository;
    }
    public List<DeviceDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(deviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDetailsDTO findDeviceById(Integer id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return deviceBuilder.toDeviceDetailsDTO(deviceOptional.get());
    }

    public Integer insert(DeviceDetailsDTO deviceDTO) {
        Device device = deviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        return device.getId();
    }

    public boolean deleteDeviceById(Integer deviceID) {
        if(deviceRepository.existsById(deviceID)){
            deviceRepository.deleteById(deviceID);
            LOGGER.debug("Device with id {} was deleted from device-db", deviceID);
            return true;
        } else {
            LOGGER.debug("There is NO device with id {} in device-db", deviceID);
            return false;
        }

    }

    public Integer update(Integer deviceID, DeviceDetailsDTO deviceDTO) {
        Device device = deviceBuilder.toEntity(deviceDTO);
        Optional<Device> deviceOptional = deviceRepository.findById(deviceID);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Cannot update. Device with id {} was not found in db", deviceID);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceID);
        }

        deviceOptional.get().setDescription(device.getDescription());
        deviceOptional.get().setAddress(device.getAddress());
        deviceOptional.get().setConsumption(device.getConsumption());

        device = deviceRepository.save(deviceOptional.get());
        return device.getId();

    }

    public Integer insertUserRef(Integer clientId) {

        Device d1 = new Device("Device","Address",10);

        List<Device> lst =  new ArrayList<>();



        Client client = new Client(clientId, lst);
        client = clientRepository.save(client);
        LOGGER.debug("Device with id {} was inserted in db", client.getId());
        return client.getId();

    }

    public List<DeviceDTO> findDevicesByClientId(Integer clientId) {
        List<Device> deviceList = deviceRepository.findByClientId(clientId);
        return deviceList.stream()
                .map(deviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());

    }
}
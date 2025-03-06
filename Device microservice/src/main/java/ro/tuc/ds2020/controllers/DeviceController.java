package ro.tuc.ds2020.controllers;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDevice(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") Integer deviceId) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7);
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("u0AWWHmoJyNObR3XPw0WltlAfXLS87NSNObR3XPw0Wl")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        String role = claims.get("role", String.class);
        if (!"admin".equals(role) && !"client".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


        DeviceDetailsDTO dto = deviceService.findDeviceById(deviceId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getDevices(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7);
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("u0AWWHmoJyNObR3XPw0WltlAfXLS87NSNObR3XPw0Wl")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        String role = claims.get("role", String.class);
        if (!"admin".equals(role) && !"client".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }



        List<DeviceDTO> dtos = deviceService.findDevices();
        for (DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(authorizationHeader, dto.getId())).withRel("deviceDetails");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/devicefor/{id}")
    public ResponseEntity<List<DeviceDTO>> getDevicesOfAClient(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") Integer clientId) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7);
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("u0AWWHmoJyNObR3XPw0WltlAfXLS87NSNObR3XPw0Wl")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        String role = claims.get("role", String.class);
        if (!"admin".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


        List<DeviceDTO> dtos = deviceService.findDevicesByClientId(clientId);
        for (DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(authorizationHeader, dto.getId())).withRel("deviceDetails");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @PostMapping(value = "/client/{id}")
    public ResponseEntity<Integer> insertUserRef( @PathVariable("id") Integer clientId) {
        Integer response = deviceService.insertUserRef(clientId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<Integer> insertDevice(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody DeviceDetailsDTO deviceDTO) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7);
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("u0AWWHmoJyNObR3XPw0WltlAfXLS87NSNObR3XPw0Wl")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        String role = claims.get("role", String.class);
        if (!"admin".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


        Integer deviceID = deviceService.insert(deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteDevice(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") Integer deviceId){
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7);
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("u0AWWHmoJyNObR3XPw0WltlAfXLS87NSNObR3XPw0Wl")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        String role = claims.get("role", String.class);
        if (!"admin".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }



        boolean isRemoved = deviceService.deleteDeviceById(deviceId);
       if(!isRemoved){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity<Integer> updateDevice(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody DeviceDetailsDTO deviceDTO, @PathVariable("id") Integer deviceID){
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7);
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("u0AWWHmoJyNObR3XPw0WltlAfXLS87NSNObR3XPw0Wl")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String role = claims.get("role", String.class);
        if (!"admin".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


        Integer updatedID = deviceService.update(deviceID, deviceDTO);
        return new ResponseEntity<>(updatedID, HttpStatus.OK);
    }


    //TODO: UPDATE, DELETE per resource

}

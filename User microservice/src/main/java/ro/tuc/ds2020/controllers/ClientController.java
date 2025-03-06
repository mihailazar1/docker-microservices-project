package ro.tuc.ds2020.controllers;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.ClientDTO;
import ro.tuc.ds2020.dtos.ClientDetailsDTO;
import ro.tuc.ds2020.services.ClientService;
import ro.tuc.ds2020.utility.JwtUtil;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping()
    public ResponseEntity<List<ClientDTO>> getClients(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7); // Extract JWT from "Bearer <token>"
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("u0AWWHmoJyNObR3XPw0WltlAfXLS87NSNObR3XPw0Wl") // Same secret used to sign the token
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Check if the user is an admin or client
        String role = claims.get("role", String.class);
        if (!"admin".equals(role) && !"client".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }



        List<ClientDTO> dtos = clientService.findClients();
        for (ClientDTO dto : dtos) {
            Link clientLink = linkTo(methodOn(ClientController.class)
                    .getClient(dto.getId(), authorizationHeader)).withRel("clientDetails");
            dto.add(clientLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDetailsDTO> getClient(@PathVariable("id") Integer clientId, @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7); // Extract JWT from "Bearer <token>"
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("u0AWWHmoJyNObR3XPw0WltlAfXLS87NSNObR3XPw0Wl") // Same secret used to sign the token
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Check if the user is an admin or client
        String role = claims.get("role", String.class);
        if (!"admin".equals(role) && !"client".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }



        ClientDetailsDTO dto = clientService.findClientById(clientId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Integer> insertClient(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody ClientDetailsDTO clientDTO) {

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


        Integer clientID = clientService.insert(clientDTO);
        return new ResponseEntity<>(clientID, HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@RequestHeader("Authorization") String authorizationHeader,
                                             @PathVariable("id") Integer clientId) {
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


        boolean isRemoved = clientService.deleteClientById(clientId);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(value = "/update/{id}")
    public ResponseEntity<Integer> updateClient(@Valid @RequestHeader("Authorization") String authorizationHeader, @RequestBody ClientDetailsDTO clientDTO, @PathVariable("id") Integer clientID){

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

        Integer updatedID = clientService.update(clientID, clientDTO);
        return new ResponseEntity<>(updatedID, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody ClientDetailsDTO clientDTO) {
      ClientDTO client = clientService.authenticate(clientDTO.getName(), clientDTO.getPassword());

      if(client != null){
          String token = JwtUtil.generateToken(client.getId().toString(), client.getRole());
          return ResponseEntity.ok(token);
      } else {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(-1);

      }


    }







}

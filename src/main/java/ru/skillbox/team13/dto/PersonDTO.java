package ru.skillbox.team13.dto;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
public class PersonDTO {
  private int id;
  private String firstName;
  private String lastName;
  private LocalDateTime regDate;
  private LocalDateTime birthDate;
  private String email;
  private String phone;
  private String password;
  private String photo;
  private String about;
  private String town;
  private String confirmationCode;
  private boolean isApproved;
  private MessagePermission messagesPermission;
  private LocalDateTime lastOnlineTime;
  private boolean isBlocked;
  public enum MessagePermission {
    ALL, FRIENDS
  }
}

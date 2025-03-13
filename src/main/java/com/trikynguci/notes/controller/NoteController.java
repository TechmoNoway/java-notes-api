package com.trikynguci.notes.controller;

import com.trikynguci.notes.model.Note;
import com.trikynguci.notes.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/create")
    ResponseEntity<?> doCreateNote(@RequestBody String content, @AuthenticationPrincipal UserDetails userDetails) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Note createdNote = noteService.createNoteForUser(userDetails.getUsername(), content);
            response.put("success", true);
            response.put("message", "Note created successfully");
            response.put("data", createdNote);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to create note");
            response.put("data", null);
            log.error("Error ", e);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/")
    ResponseEntity<?> doGetUserNotes(@RequestParam String username) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("success", true);
            response.put("message", "Notes fetched successfully");
            response.put("data", noteService.getNotesForUser(username));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to fetch notes");
            response.put("data", null);
            log.error("Error ", e);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{noteId}")
    ResponseEntity<?> doUpdateNote(@PathVariable Long noteId, @RequestBody String content, @AuthenticationPrincipal UserDetails userDetails) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Note updatedNote = noteService.updateNoteForUser(noteId, userDetails.getUsername(), content);
            response.put("success", true);
            response.put("message", "Note updated successfully");
            response.put("data", updatedNote);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update note");
            response.put("data", noteId);
            log.error("Error ", e);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{noteId}")
    ResponseEntity<?> doDeleteNote(@PathVariable Long noteId, @AuthenticationPrincipal UserDetails userDetails) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            noteService.deleteNoteForUser(noteId, userDetails.getUsername());
            response.put("success", true);
            response.put("message", "Note deleted successfully");
            response.put("data", noteId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to delete note");
            response.put("data", null);
            log.error("Error ", e);
            return ResponseEntity.badRequest().body(response);
        }
    }

}

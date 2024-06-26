package com.ecobank.idea.controller;

import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.comment.CommentDTO;
import com.ecobank.idea.dto.comment.CommentReplyDTO;
import com.ecobank.idea.entity.Comment;
import com.ecobank.idea.exception.ErrorResponseDTO;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@Tag(
        name = "Comment API",
        description = "CRUD API to CREATE, UPDATE, FETCH and DELETE comments"
)
@Slf4j
@RestController
@RequestMapping(path = API_BASE_URL + "/idea", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "Fetch Comment API",
            description = "Fetch comments for an Idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS Idea not found to retrieve comments"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getCommentsByIdeaId(@RequestParam(required = true) Long ideaId) {
        List<Comment> comments = commentService.getCommentsByIdeaId(ideaId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @Operation(
            summary = "Create Comment API",
            description = "Add comments for an Idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS Idea not found to add comments"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping("/comment")
    public ResponseEntity<ResponseDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        commentService.createComment(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Comment created successfully"));
    }

    @Operation(
            summary = "Reply Comment API",
            description = "Reply comments for an Idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS Comment not found to reply"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping("/comment/reply")
    public ResponseEntity<ResponseDTO> replyComment(@Valid @RequestBody CommentReplyDTO commentReplyDTO) {
        commentService.replyToComment(commentReplyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Comment replied successfully"));
    }

    @Operation(
            summary = "Delete Comment API",
            description = "Delete comments for an Idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS Comment not found to delete"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @DeleteMapping("/comment")
    public ResponseEntity<ResponseDTO> deleteComment(@RequestParam(required = true) String commentId) {
        boolean isCommentDeleted = commentService.deleteComment(commentId);
        if (!isCommentDeleted) {
            throw new ResourceNotFoundException("Comment not found!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Comment deleted successfully"));
    }

    @Operation(
            summary = "Update Comment API",
            description = "Update comments for an Idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS Comment not found to update"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PutMapping("/comment")
    public ResponseEntity<ResponseDTO> updateComment(@RequestBody CommentDTO commentDTO, @RequestParam(required = true) String commentId) {
        commentService.updateComment(commentDTO, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Comment updated successfully"));
    }
}

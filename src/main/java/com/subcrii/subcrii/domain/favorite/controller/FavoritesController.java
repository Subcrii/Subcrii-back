package com.subcrii.subcrii.domain.favorite.controller;

import com.subcrii.subcrii.domain.favorite.dto.FavoriteCreatorsResponse;
import com.subcrii.subcrii.domain.favorite.service.FavoritesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoritesController {
    private final FavoritesService favoritesService;

    @PostMapping
    @Operation(
            summary = "즐겨찾기 추가",
            description = "멤버아이디와 크리에이터 아이디로 즐겨찾기를 추가합니다.",
            parameters = {
                @Parameter(
                        name = "memberId",
                        description = "현재 로그인 회원 ID",
                        required = true,
                        schema = @Schema(type = "UUID", format = "UUID", example = "11111111-1111-1111-1111-111111111111")
                ),
                @Parameter(
                        name = "creatorId",
                        description = "즐겨찾기할 크리에이터 ID",
                        required = true,
                        schema = @Schema(type = "UUID", format = "UUID", example = "11111111-1111-1111-1111-111111111111")
                )
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "즐겨찾기 추가 성공"
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "잘못된 요청"
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "회원 또는 크리에이터를 찾을 수 없음"
                )
            })
    public ResponseEntity<Void> addFavorites(UUID memberId, UUID creatorId) {
        favoritesService.addFavorites(memberId, creatorId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(
            summary = "즐겨찾기 삭제",
            description = "멤버아이디와 크리에이터 아이디로 즐겨찾기를 삭제합니다.",
            parameters = {
                @Parameter(
                        name = "memberId",
                        description = "현재 로그인 회원 ID",
                        required = true,
                        schema = @Schema(type = "UUID", format = "UUID", example = "11111111-1111-1111-1111-111111111111")
                ),
                @Parameter(
                        name = "creatorId",
                        description = "즐겨찾기 해제할 크리에이터 ID",
                        required = true,
                        schema = @Schema(type = "UUID", format = "UUID", example = "11111111-1111-1111-1111-111111111111")
                )
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "즐겨찾기 해제 성공"
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "잘못된 요청"
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "회원 또는 크리에이터를 찾을 수 없음"
                )
            })
    public ResponseEntity<Void> deleteFavorites(UUID memberId, UUID creatorId) {
        favoritesService.removeFavorites(memberId, creatorId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/creators")
    @Operation(
            summary = "즐겨찾기 목록",
            description = "멤버아이디로 즐겨찾기한 크리에이터 목록을 반환합니다.",
            parameters = {
                    @Parameter(
                            name = "memberId",
                            description = "현재 로그인 회원 ID",
                            required = true,
                            schema = @Schema(type = "UUID", format = "UUID", example = "11111111-1111-1111-1111-111111111111")
                    )},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "목록 반환 성공"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "회원 또는 크리에이터를 찾을 수 없음"
                    )
            })
    public ResponseEntity<Page<FavoriteCreatorsResponse>>  findFavoriteCreatorsByMemberId(UUID memberId, int page, int size) {
        Page<FavoriteCreatorsResponse> favoriteCreatorsResponse = favoritesService.findFavoriteCreatorsByMemberId(
                memberId, page, size);
        return  ResponseEntity.ok(favoriteCreatorsResponse);
    }
}

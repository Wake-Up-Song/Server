package com.example.wakeUp.domain.song.service;

import com.example.wakeUp.domain.song.presentation.dto.request.CreateSongRequestDto;
import com.example.wakeUp.domain.song.domain.Song;
import com.example.wakeUp.domain.song.domain.repository.SongRepository;
import com.example.wakeUp.domain.song.facade.SongFacade;
import com.example.wakeUp.domain.song.presentation.dto.response.SongResponseDto;
import com.example.wakeUp.domain.user.domain.User;
import com.example.wakeUp.domain.user.domain.repository.UserRepository;
import com.example.wakeUp.domain.user.facade.UserFacade;
import com.example.wakeUp.global.Utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongService {

    private final SongRepository songRepository;
    private final UserFacade userFacade;
    private final SongFacade songFacade;

    @Transactional
    public void requestSong(CreateSongRequestDto dto) {
        User user = userFacade.getCurrentUser();
        songFacade.validateRequestSong(dto, user);
        songRepository.save(dto.toEntity(user));

    }

    public void deleteSong(Long id) {
        Song song = songFacade.findSongById(id);
        songRepository.delete(song);
    }

    @Transactional(readOnly = true)
    public List<SongResponseDto> getSongChart() {
        return songRepository.findAllByCreatedAtBetween(DateUtil.getToday(), DateUtil.getTomorrow()).stream()
                .map(SongResponseDto::of)
                .collect(Collectors.toList());
    }
}

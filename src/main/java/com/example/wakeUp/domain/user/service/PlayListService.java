package com.example.wakeUp.domain.user.service;

import com.example.wakeUp.domain.user.domain.PlayList;
import com.example.wakeUp.domain.user.domain.User;
import com.example.wakeUp.domain.user.domain.repository.PlayListRepository;
import com.example.wakeUp.domain.user.facade.PlayListFacade;
import com.example.wakeUp.domain.user.facade.UserFacade;
import com.example.wakeUp.domain.user.presentation.dto.request.AddPlayListRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayListService {

    private final UserFacade userFacade;
    private final PlayListRepository playListRepository;
    private final PlayListFacade playListFacade;

    @Transactional
    public void addPlayList(AddPlayListRequestDto dto) {
        User user = userFacade.findByEmail(userFacade.getCurrentUser().getEmail());
        playListFacade.validateAddPlayList(user, dto.getTitle(), dto.getSinger());

        PlayList playList = dto.toEntity();
        playList.setRelation(user);

        playListRepository.save(playList);
    }

    @Transactional
    public void deletePlayList(Long id) {
        playListRepository.deleteById(id);
    }
}

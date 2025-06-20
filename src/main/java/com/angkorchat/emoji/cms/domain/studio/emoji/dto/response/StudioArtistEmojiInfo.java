package com.angkorchat.emoji.cms.domain.studio.emoji.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class StudioArtistEmojiInfo {
    private EmojiStatusCount emojiStatusCount;
    private Page<StudioArtistEmojiList> studioArtistEmojiListPage;
}

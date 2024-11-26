package icu.burtry.writespaceadmin.controller;

import icu.burtry.writespaceadmin.service.IChannelService;
import icu.burtry.writespacemodel.dto.ChannelDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.PageQueryDTO;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/channel")
@Slf4j
public class ChannelController {

    @Autowired
    private IChannelService channelService;

    @PostMapping()
    public Result addChannel(@RequestBody ChannelDTO channelDTO) {
        log.info("添加频道：{}",channelDTO);
        return channelService.addChannel(channelDTO);
    }

    @GetMapping()
    public Result<PageDTO<Channel>> channelList(PageQueryDTO queryDTO) {
        log.info("获取频道列表");
        PageDTO<Channel> channelList = channelService.channelList(queryDTO);
        return Result.success(channelList,"获取成功!");
    }

}

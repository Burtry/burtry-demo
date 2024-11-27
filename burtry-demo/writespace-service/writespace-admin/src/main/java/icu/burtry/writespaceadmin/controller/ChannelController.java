package icu.burtry.writespaceadmin.controller;

import icu.burtry.writespaceadmin.service.IChannelService;
import icu.burtry.writespacemodel.dto.ChannelDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.PageQueryDTO;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespaceutils.result.Result;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
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

    @PostMapping("/{id}")
    public Result enableChannel(@PathVariable @NonNull Long id) {
        log.info("启用/禁用频道:{}",id);
        return channelService.enableChannel(id);
    }

    @PutMapping()
    public Result updateChannel(@RequestBody ChannelDTO channelDTO) {
        log.info("修改频道信息:{}", channelDTO);
        return channelService.updateChannel(channelDTO);
    }

    @DeleteMapping()
    public Result deleteChannel(@NonNull Long id) {
        log.info("删除频道:{}",id);
        return channelService.deleteChannel(id);
    }

}

package com.finalproject.ispan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.ispan.dto.RankDTO;
import com.finalproject.ispan.service.RankService;

@RestController
@RequestMapping("/api/ranks")
public class RankController {

    @Autowired
    private RankService rankService;

    /**
     * 新增 Rank
     */
    @PostMapping
    public ResponseEntity<RankDTO> addRank(@RequestBody RankDTO rankDTO) {
        RankDTO createdRank = rankService.addRank(rankDTO);
        return ResponseEntity.ok(createdRank);
    }

    /**
     * 查詢所有 Ranks
     */
    @GetMapping
    public ResponseEntity<List<RankDTO>> getAllRanks() {
        List<RankDTO> ranks = rankService.getAllRanks();
        return ResponseEntity.ok(ranks);
    }

    /**
     * 查詢單一 Rank 透過 rankId
     */
    @GetMapping("/{rankID}")
    public ResponseEntity<RankDTO> getRank(@PathVariable Long rankID) {
        RankDTO rank = rankService.getRankById(rankID);
        return ResponseEntity.ok(rank);
    }

    /**
     * 修改 Rank 透過 rankId
     */
    @PutMapping("/{rankID}")
    public ResponseEntity<RankDTO> updateRank(@PathVariable Long rankID, @RequestBody RankDTO rankDTO) {
        RankDTO updatedRank = rankService.updateRank(rankID, rankDTO);
        return ResponseEntity.ok(updatedRank);
    }

    /**
     * 刪除 Rank 透過 rankId
     */
    @DeleteMapping("/{rankID}")
    public ResponseEntity<String> deleteRank(@PathVariable Long rankID) {
        rankService.deleteRank(rankID);
        return ResponseEntity.ok("Rank with ID " + rankID + " has been deleted.");
    }
}

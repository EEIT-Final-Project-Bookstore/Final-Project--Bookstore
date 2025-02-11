package bookstore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.dto.RankDTO;
import bookstore.domain.RankBean;
import bookstore.repository.RankRepository;

@Service
public class RankService {

    @Autowired
    private RankRepository rankRepository;

    /**
     * 新增 Rank
     */
    public RankDTO addRank(RankDTO rankDTO) {
        RankBean rankBean = new RankBean();
        rankBean.setRankName(rankDTO.getRankName());

        RankBean savedRank = rankRepository.save(rankBean);

        RankDTO createdDTO = new RankDTO();
        createdDTO.setRankId(savedRank.getRankID());
        createdDTO.setRankName(savedRank.getRankName());

        return createdDTO;
    }

    /**
     * 查詢所有 Ranks
     */
    public List<RankDTO> getAllRanks() {
        return rankRepository.findAll().stream().map(rank -> {
            RankDTO dto = new RankDTO();
            dto.setRankId(rank.getRankID());
            dto.setRankName(rank.getRankName());
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 查詢單一 Rank 透過 rankId
     */
    public RankDTO getRankById(Long rankId) {
        RankBean rank = rankRepository.findById(rankId)
                .orElseThrow(() -> new RuntimeException("Rank not found"));

        RankDTO dto = new RankDTO();
        dto.setRankId(rank.getRankID());
        dto.setRankName(rank.getRankName());

        return dto;
    }

    /**
     * 更新 Rank
     */
    public RankDTO updateRank(Long rankId, RankDTO rankDTO) {
        RankBean rank = rankRepository.findById(rankId)
                .orElseThrow(() -> new RuntimeException("Rank not found"));

        rank.setRankName(rankDTO.getRankName()); // 僅更新名稱
        RankBean updatedRank = rankRepository.save(rank);

        RankDTO updatedDTO = new RankDTO();
        updatedDTO.setRankId(updatedRank.getRankID());
        updatedDTO.setRankName(updatedRank.getRankName());

        return updatedDTO;
    }

    /**
     * 刪除 Rank
     */
    public void deleteRank(Long rankId) {
        RankBean rank = rankRepository.findById(rankId)
                .orElseThrow(() -> new RuntimeException("Rank not found"));

        rankRepository.delete(rank);
    }
}

package bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.domain.RankBean;

public interface RankRepository extends JpaRepository<RankBean, Long> {
    // 查詢 Rank 透過 rankName
    Optional<RankBean> findByRankName(String rankName);

    // 刪除 Rank 透過 rankName
    void deleteByRankName(String rankName);

    // 確認是否存在 Rank 透過 rankName
    boolean existsByRankName(String rankName);
}

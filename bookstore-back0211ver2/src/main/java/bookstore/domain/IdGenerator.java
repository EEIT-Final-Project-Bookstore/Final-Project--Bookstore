package bookstore.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
    private static final AtomicInteger counter = new AtomicInteger(1);
    private static String lastTimestamp = "";

    public synchronized String generateId() {
        // 產生時間戳 (yyyyMMddHHmm) → 12 碼（只到分鐘）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String timestamp = sdf.format(new Date());
        // 如果時間戳變了（新的一分鐘），則重置計數器
        if (!timestamp.equals(lastTimestamp)) {
            lastTimestamp = timestamp;
            counter.set(1);
        }
        // 取得當前計數 (3 碼，範圍 001~999)
        int sequence = counter.getAndIncrement() % 1000; // 讓序號循環 001~999
        String sequenceStr = String.format("%03d", sequence); // 轉換為三位數，確保補 0
        // 組合訂單號碼
        return "BK" + timestamp + sequenceStr; // 總長度：12+3 = 15 碼
    }
}
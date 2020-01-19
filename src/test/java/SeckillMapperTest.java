import com.wkj.project.Start;
import com.wkj.project.entity.Seckill;
import com.wkj.project.mapper.SeckillMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest

@ContextConfiguration(classes = Start.class)
@Slf4j
public class SeckillMapperTest {

    @Autowired
    private SeckillMapper seckillMapper;

    @Test
    public void findAll() {
        System.out.println("findAll,test method");
        List<Seckill> all = seckillMapper.findAll();
        for (Seckill seckill : all) {
            log.info(seckill.getTitle());
        }
    }

    @Test
    public void findById() {
    }

    @Test
    public void reduceStock() {
    }

}

package ee.lars.spring.soundsystem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CDPlayerConfig.class)
public class CDPlayerTest {

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Autowired
    private MediaPlayer mediaPlayer;

    @Autowired
    private CompactDisc cd;
    
    @Test
    public void should_inject_a_cd() throws Exception {
        // given
        
        // when
        
        // then
        assertThat(this.cd).isNotNull();
    }
    
    @Test
    public void injected_bean_is_sgt_peppers() throws Exception {
        // given
        
        // when
        
        // then
        assertThat(this.cd.getClass().getName()).isEqualTo("ee.lars.spring.soundsystem.SgtPeppers");
    }
    
    @Test
    public void mediaplayer_should_play_cd_when_play_is_called() throws Exception {
        // given
        
        // when
        this.mediaPlayer.play();
        
        // then
        assertThat(this.log.getLog()).isEqualTo("Playing Sgt. Pepper's Lonely Hearts Club Band by The Beatles\n");
    }
}

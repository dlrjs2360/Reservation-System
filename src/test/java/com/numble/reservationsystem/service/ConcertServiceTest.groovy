package com.numble.reservationsystem.service

import com.numble.reservationsystem.entity.Category
import com.numble.reservationsystem.entity.ConcertState
import com.numble.reservationsystem.entity.domain.Concert
import com.numble.reservationsystem.entity.domain.User
import com.numble.reservationsystem.entity.dto.concert.ConcertRequestDto
import com.numble.reservationsystem.repository.ConcertRepository
import com.numble.reservationsystem.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDate;

@SpringBootTest
@Transactional
class ConcertServiceTest extends Specification {

    @Autowired
    ConcertRepository showRepository

    @Autowired
    ConcertService showService

    @Autowired
    UserRepository userRepository

    void setup() {
        def User user = User.builder()
                .name("testUser")
                .email("test@test.com")
                .password("testPassword")
                .phone("010-0000-0000")
                .role("MANAGER")
                .build()

        def User testUser = userRepository.save(user)
    }

    def "공연 생성 테스트"() {
        given:
        ConcertRequestDto showRequestDto = ConcertRequestDto.builder()
                .title("test")
                .category(Category.GAME)
                .content("content")
                .date(LocalDate.now())
                .startTime("11:00")
                .endTime("13:00")
                .location("Seoul")
                .price(30000)
                .curState(ConcertState.OPENED)
                .build()

        Concert show = Concert.toEntity(showRequestDto, testUser)


    }

}
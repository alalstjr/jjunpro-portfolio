package com.jjunpro.project.util;

import com.jjunpro.project.domain.Store;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StoreUtil {

    private final StoreRepository storeRepository;

    /*
     * { DATA DB } 에 음식점의 정보를 조회
     * { DB } 에 음식점의 정보가 존재한다면 { Store DB } 에 저장한 University 값을 추가합니다.
     * { DB } 에 음식점의 정보가 없다면 음식점의 ID 정보를 추가
     *
     * University 작성이 아닌경우에는 { Store DB } 값만 조회 or 추가합니다.
     */
    public Store storeDataHandler(
            String stoId,
            String stoName,
            String stoAddress,
            String stoUrl,
            University university
    ) {
        Optional<Store> storeData = storeRepository.findByStoId(stoId);

        if (storeData.isPresent()) {
            /* University 작성시 추가되는 정보 */
            if (university != null) {
                storeData
                        .get()
                        .addUniversity(university);

                storeRepository.save(storeData.get());
            }
            else {
                return storeData.get();
            }
        }
        else {
            /* Store 정보를 등록 */
            Store store = new Store(
                    stoId,
                    stoName,
                    stoAddress,
                    stoUrl
            );
            if (university != null) {
                store.addUniversity(university);
            }

            return storeRepository.save(store);
        }

        return null;
    }
}

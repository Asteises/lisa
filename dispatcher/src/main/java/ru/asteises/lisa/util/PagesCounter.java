package ru.asteises.lisa.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class PagesCounter {

    private int pageFrom;

    public void getCount(Navigate navigate) {
        if (navigate.equals(Navigate.NEXT)) {
            pageFrom += 10;
        } else if (navigate.equals(Navigate.PREV) && pageFrom >= 10) {
            pageFrom -=10;
        } else {
            throw new RuntimeException("Wrong navigation or count");
        }
    }
}

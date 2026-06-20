package simple.food.backend.model.tabelanutricional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import simple.food.backend.dto.tabelanutricional.TabelaNutricionalResponse;
import simple.food.backend.infrastructur.exception.ErrorMessages;
import simple.food.backend.infrastructur.exception.ServiceException;

import java.util.List;

@Service
public class TabelaNutricionalService {

    @Autowired
    private TabelaNutricionalRepository tabelaNutricionalRepository;

    public TabelaNutricional findById(Long id) {
        return tabelaNutricionalRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorMessages.FOOD_ITEM_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public List<TabelaNutricionalResponse> findFoodsByName(String description) {
        List<TabelaNutricional> tabelaNutricionalList
                = tabelaNutricionalRepository.findByDescricaoDosAlimentosContainingIgnoreCase(description);

        return tabelaNutricionalList.stream().map(TabelaNutricionalResponse::new).toList();
    }
}

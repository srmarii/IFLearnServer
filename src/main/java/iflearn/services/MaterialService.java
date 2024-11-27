package iflearn.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import iflearn.dto.MaterialResponse;
import iflearn.entities.Material;
import iflearn.repositories.MaterialRepository;

@Service
public class MaterialService {

	@Autowired
	private MaterialRepository mr;

	public ResponseEntity<?> create(Material m) {
		if (m.getNome() == null || m.getUsuario() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		Material mNovo = mr.save(m);
		return ResponseEntity.ok(mNovo);
	}

	public ResponseEntity<?> read(Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}
		Optional<Material> mExistente = mr.findById(id);
		if (mExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			Material m = mExistente.get();
			return ResponseEntity.ok(new MaterialResponse(m));
		}
	}

	public ResponseEntity<?> update(Material m) {
		if (m.getId() == null || m.getNome() == null || m.getUsuario() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		Optional<Material> mExistente = mr.findById(m.getId());
		if (mExistente.isEmpty())
			return ResponseEntity.notFound().build();
		else {
			try {
//				Usuario u = ur.findById(m.getUsuario().getId()).get();
				Material mAtualizado = mr.save(m);
				return ResponseEntity.ok(new MaterialResponse(mAtualizado));
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
	}

	public ResponseEntity<?> delete(Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}
		Optional<Material> mExistente = mr.findById(id);
		if (mExistente.isEmpty())
			return ResponseEntity.notFound().build();
		else {
			mr.deleteById(id);
			return ResponseEntity.ok().body("material deletado");
		}
	}

	public ResponseEntity<List<MaterialResponse>> listarTodos() {
		List<Material> lista = mr.findAll();
		return ResponseEntity.ok(lista.stream()
				.map(MaterialResponse::new)
				.collect(Collectors.toList()));

	}

}

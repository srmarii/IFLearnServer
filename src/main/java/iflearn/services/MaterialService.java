package iflearn.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import iflearn.dto.MaterialResponse;
import iflearn.entities.Material;
import iflearn.entities.Usuario;
import iflearn.repositories.MaterialRepository;
import iflearn.repositories.UsuarioRepository;

@Service
public class MaterialService {

	@Autowired
	private MaterialRepository mr;
	@Autowired
	private UsuarioRepository ur;

	public ResponseEntity<?> create(String nome, Integer idUsuario, MultipartFile file) {
		if (nome == null || idUsuario == null || file == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}

		//////////
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
		}

		try {
			String path = "src/main/resources/static/iflearn/uploads";
			byte[] bytes = file.getBytes();
			File uploadFile = new File(path + "/" + file.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(uploadFile);
			fos.write(bytes);
			fos.flush();
			fos.close();

			// return ResponseEntity.status(HttpStatus.OK).body("O arquivo foi baixado: " +
			// file.getOriginalFilename());
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
		}
		////////
		Usuario u;
		Optional<Usuario> uExistente = ur.findById(idUsuario);
		if (uExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			u = uExistente.get();
		}

		Material m = new Material(nome, file.getOriginalFilename(), u);
		Material mNovo = mr.save(m);

		return ResponseEntity.ok(new MaterialResponse(mNovo));
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
		if (m.getId() == null || m.getNome() == null || m.getNomeArquivo() == null || m.getUsuario() == null) {
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
		return ResponseEntity.ok(lista.stream().map(MaterialResponse::new).collect(Collectors.toList()));

	}

}

package com.sfileuploader.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sfileuploader.entity.Album;
import com.sfileuploader.entity.Pds;
import com.sfileuploader.repository.AlbumRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PdsModuleController extends MyBaseControllerPds {

	private final AlbumRepository albumRepository;

	@GetMapping(value = "/")
	public String index() {
		return "redirect:/pds";
	}

	@GetMapping(value = "/pds")
	public String create(Model model, HttpServletRequest request) {
		Album album = new Album();
		model.addAttribute("album", album);
		return "pds/upload";
	}

	@PostMapping(value = "/pds")
	public String save(Model model, @Valid @ModelAttribute("album") Album album, BindingResult result,
			@RequestParam("file") MultipartFile[] files) {

		List<String> pdsFileList = new ArrayList<>();

		for (MultipartFile multipartFile : files) {
			pdsFileList.add(uploadFile(multipartFile));
		}

		if (Objects.nonNull(album.getPdsList())) {

			var pdsIndex = 0;
			for (Pds pds : album.getPdsList()) {

				if (pdsFileList.size() > 0) {
					pds.setAttachment(pdsFileList.get(pdsIndex));
					pds.setAlbum(album);
					pdsIndex++;
				}
			}
		}
		albumRepository.save(album);
		return "redirect:/pds";
	}
}

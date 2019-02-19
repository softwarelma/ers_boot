package com.softwarelma.ers_boot;

import java.io.Serializable;

public interface ArticoloDaoInterface extends Serializable {

	public ArticoloListResponse getAll();

	public ArticoloResponse get(Long id);

	public ArticoloResponse addNew(Articolo articolo);

	public ArticoloResponse putExisting(Articolo articolo);

	public ArticoloResponse putExistingOrNew(Articolo articolo);

	public ArticoloResponse delete(Long id);

}

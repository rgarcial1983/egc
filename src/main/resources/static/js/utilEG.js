function eliminarRegistro(id) {
	Swal.fire({
		  title: '¿Está seguro de que desea eliminar el registro?',
		  text:  'Esta operación no se podrá revertir',
		  icon:  'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Sí, elimínalo!'
	}).then((result) => {
		  if (result.value) {
			  $.ajax({
				  url: "/usuario/eliminar/" + id,
				  success: function(res) {
					  console.log(res);
				  }
			  })
		    Swal.fire(
		      'Deleted!',
		      'Se ha eliminado el registro',
		      'success'
		    ).then((OK)=>{
		    	if(OK) {
		    		location.href="/usuario/listar";
		    	}
		    });
		  }
	})
}

function eliminarRegistro(id, titulo, texto, url, urlOk) {
	Swal.fire({
		  title: titulo,
		  text:  texto,
		  icon:  'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Sí, elimínalo!'
	}).then((result) => {
		  if (result.value) {
			  $.ajax({
				  url: url + id,
				  success: function(res) {
					  console.log(res);
				  }
			  })
		    Swal.fire(
		      'Deleted!',
		      'Se ha eliminado el registro',
		      'success'
		    ).then((OK)=>{
		    	if(OK) {
		    		location.href=urlOk;
		    	}
		    });
		  }
	})
}
/** Autor: Christopher Monteiro
 *  Framework: AngularJS
 */

var app = angular.module('CartuchosApp', []);

app.run(function($rootScope, $http) {
	$rootScope.cartuchos = [];
	$rootScope.departamentos = [];
	$rootScope.registros = [];
	$rootScope.usuarios = [];
	$rootScope.usuarioLogado = {};
	
	$rootScope.listaCartuchos = function() {
		$rootScope.cartuchos = [];
		$http.get("cartuchos")
		.then(function(response) {
			$rootScope.cartuchos = response.data;
		}, function(reason) {
			alert("Falha\n" + reason.status + ": " + reason.statusText);
		});
	}
	
	$rootScope.listaDepartamentos = function() {
		$rootScope.departamentos = [];
		$http.get("departamentos")
		.then(function(response) {
			$rootScope.departamentos = response.data;
		}, function(reason) {
			alert("Falha\n" + reason.status + ": " + reason.statusText);
		});
	}
	
	$rootScope.listaRegistros = function() {
		$http.get("registros")
		.then(function(response) {
			$rootScope.registros = response.data;
		}, function(reason) {
			alert("Falha\n" + reason.status + ": " + reason.statusText);
		});
	}
	
	$rootScope.listaUsuarios = function() {
		$http.get("usuarios")
		.then(function(response) {
			$rootScope.usuarios = response.data;
		}, function(reason) {
			alert("Falha\n" + reason.status + ": " + reason.statusText);
		});
	}
	
	$rootScope.bucaUsuarioLogado = function() {
		$http.get("usuarios/logado")
		.then(function(response) {
			$rootScope.usuarioLogado = response.data;
		}, function(reason) {
			alert("Falha\n" + reason.status + ": " + reason.statusText);
		});
	}
	
	$rootScope.limpaSenha = function() {
		$rootScope.usuarioLogado.senha = "";
		$rootScope.usuarioLogado.senha2 = "";
		$rootScope.usuarioLogado.senhaAtual = "";
	}
});

app.controller('menuCtrl', function($scope, $rootScope, $http) {
	$scope.telaInicio = function() {
		$rootScope.tela = "inicio";
		$rootScope.listaCartuchos();
	}
	
	$scope.telaCartucho = function() {
		$rootScope.tela = "cartucho";
		$rootScope.listaCartuchos();
	}
	
	$scope.telaDepartamento = function() {
		$rootScope.tela = "departamento";
		$rootScope.listaDepartamentos();
	}
	
	$scope.telaRegistro = function() {
		$rootScope.tela = "registro";
		$rootScope.listaRegistros();
	}
	
	$scope.carregaSelects = function() {
		$rootScope.listaCartuchos();
		$rootScope.listaDepartamentos();
	}
				
	$scope.telaInicio();
	$rootScope.bucaUsuarioLogado();
});

app.controller('inicioCtrl', function($scope, $rootScope, $http) {
	$scope.carregaSelects = function() {
		$rootScope.listaCartuchos();
		$rootScope.listaDepartamentos();
	}
});

app.controller('cartuchoCtrl', function($scope, $rootScope, $http) {
	$scope.saveCartucho = function() {
				
		if ($scope.cartucho.id == null) {
			$http.post("cartuchos", $scope.cartucho)
			.then(function() {
				$scope.fechaModalCartucho();
			}, function(reason) {
				alert("Falha\n" + 
						reason.status + ": " + reason.statusText);
			});
		} else {
			$http.put("cartuchos/" + $scope.cartucho.id, 
					$scope.cartucho)
			.then(function() {
				$scope.fechaModalCartucho();
			}, function(reason) {
				alert("Falha\n" + reason.status + ": " + reason.statusText);
			});
		}
	}
	
	$scope.loadCartucho = function(cartucho) {
		$scope.cartucho = angular.copy(cartucho);
	}
	
	$scope.fechaModalCartucho = function() {
		$rootScope.listaCartuchos();
		$('#modalCartucho').modal('toggle');
	}
	
	$scope.novoCartucho = function() {
		$scope.cartucho = {};
		$scope.cartucho.ativo = true;
	}
	
	$scope.removeCartucho = function() {
		$http.delete("cartuchos/"+ $scope.cartucho.id)
		.then(function() {
			$scope.fechaModalCartucho();
		}, function(reason) {
			alert("Falha\n" + reason.status + ": " + reason.statusText);
		});
	}
});

app.controller('departamentoCtrl', function($scope, $rootScope, $http) {
	$scope.loadDepartamento = function(departamento) {
		$scope.departamento = angular.copy(departamento);
	}
	
	$scope.novoDepartamento = function() {
		$scope.departamento = {};
		$scope.departamento.ativo = true;
	}
	
	$scope.fechaModalDepartamento = function() {
		$rootScope.listaDepartamentos();
		$('#modalDepartamento').modal('toggle');
	}
	
	$scope.saveDepartamento = function() {
		if ($scope.departamento.id == null) {
			$http.post("departamentos", $scope.departamento)
			.then(function(){
				$scope.fechaModalDepartamento();
			}, function(reason) {
				alert("Falha\n" + 
						reason.status + ": " + reason.statusText);
			});
		} else {
			$http.put("departamentos/" + $scope.departamento.id, 
					$scope.departamento)
			.then(function(){
				$scope.fechaModalDepartamento();
			}, function(reason) {
				alert("Falha\n" + reason.status + ": " + reason.statusText);
			});
		}
	}
	
	$scope.removeDepartamento = function() {
		$http.delete("departametos")
		.then(function() {
			$scope.fechaModalDepartamento();
		}, function(reason) {
			alert("Falha\n" + reason.status + ": " + reason.statusText);
		});
	}
});

app.controller('estqEntradaCtrl', function($scope, $http, $rootScope) {
	$('#modalEntrada').on('show.bs.modal', function () {
		$scope.novaEntrada();
    })
	
	$scope.novaEntrada = function() {
		$scope.registro = {};
		$scope.registro.quantidade = 1;
	}
	
	$scope.saveEntrada = function() {
		var hoje = new Date().getTime();
		var departamento = {};
		departamento.id = 1;
		$rootScope.bucaUsuarioLogado();
		
		$scope.registro.departamento = departamento;
		$scope.registro.data = hoje;
		$scope.registro.operacao = "ENTRADA";
		$scope.registro.usuario = $rootScope.usuarioLogado;
		
		$http.post("registros", $scope.registro)
		.then(function() {
			$scope.fechaModalEntrada();
		}, function(reason) {
			alert("Falha\n" + reason.status + ": " + reason.statusText);
		});
	}
	
	$scope.fechaModalEntrada = function() {
		$rootScope.listaCartuchos();
		$('#modalEntrada').modal('toggle');
	}
	
});

app.controller('estqSaidaCtrl', function($scope, $http, $rootScope) {
	$('#modalSaida').on('show.bs.modal', function () {
		$scope.novaEntrada();
    })
    
    $scope.novaEntrada = function() {
		$scope.registro = {};
		$scope.registro.quantidade = 1;
	}
	
	$scope.saveSaida = function() {
		var hoje = new Date().getTime();
		$rootScope.bucaUsuarioLogado();
		
		$scope.registro.data = hoje;
		$scope.registro.operacao = "SAIDA";
		$scope.registro.usuario = $rootScope.usuarioLogado;
		
		$http.post("registros", $scope.registro)
		.then(function() {
			$scope.fechaModalSaida();
		}, function(reason) {
			alert("Falha\n" + reason.status + ": " + reason.statusText);
		});
	}
	
	$scope.fechaModalSaida = function() {
		$rootScope.listaCartuchos();
		$('#modalSaida').modal('toggle');
	}
});

app.controller('registroCtrl', function($scope) {     	
	$scope.de = new Date().toLocaleDateString();
	$scope.para = new Date().toLocaleDateString();
	
});

app.controller('usuarioCtrl', function($scope, $rootScope, $http) {     	
	$rootScope.listaUsuarios();
	
	$scope.novoUsuario = function() {
		$scope.usuario = {};
		$scope.usuario.username = "";
		$scope.usuario.senha = "";
		$scope.usuario.enabled = true;
		$scope.usuario.role = "USER";
	}
	
	$scope.loadUsuario = function(usuario) {
		$scope.usuario = angular.copy(usuario);
		$scope.usuario.senha = "";
		$scope.usuario.senha2 = "";
	}
	
	$scope.fechaModalUsuario = function() {
		$rootScope.listaUsuarios();
		$("#modalUsuario").modal('toggle');
	}
	
	$scope.saveUsuario = function() {
		if ($scope.usuario.id == null) {
			$http.post("usuarios", $scope.usuario)
			.then(function() {
				$scope.fechaModalUsuario();
			}, function(reason) {
				alert("Falha\n" + 
						reason.status + ": " + reason.statusText);
			});
		} else {
			$http.put("usuarios/" + $scope.usuario.id, 
					$scope.usuario)
			.then(function() {
				$scope.fechaModalUsuario();
			}, function(reason) {
				alert("Falha\n" + reason.status + ": " + reason.statusText);
			});
		}
	}
});

app.controller('senhaCtrl', function($scope, $rootScope, $http) {
	$scope.alteraSenha = function() {
		$http.put("usuarios/logado", $rootScope.usuarioLogado)
		.then(function() {
			$scope.fechaModalSenha();
		}, function(reason) {
			if (reason.status == 401) {
				alert("Senha atual incorreta.");
			} else if (reason.status == 406) {
				alert("Nova senha não atende aos requisitos de segurança.");
			} else {
				alert("Falha\n" + reason.status + ": " + reason.statusText);
			}
		});
	}
	
	$scope.fechaModalSenha = function() {
		$("#modalSenha").modal('toggle');
	}
});

app.filter("dateFilter", function() {
    return function datefilter(items, from, to) {
    	
    	var parts = from.split('/');
    	var fromDate = new Date(parts[2], parts[1] - 1, parts[0]);
    	
    	parts = to.split('/');
    	var toDate = new Date(parts[2], parts[1] - 1, parts[0]);
    	
        var result = [];
        angular.forEach(items, function(value){
        	var dataItem = new Date(value.data);
            if (dataItem >= fromDate && dataItem.getDate() <= toDate.getDate())  {
                result.push(value);
             }
         });
         return result;
     };
 });

$(function() {
    $(".datepicker").datepicker({
    	dateFormat: 'dd/mm/yy'
    });
    
    $('#username').on('keypress', function(e) {
        if (e.which == 32)
            return false;
    });
});
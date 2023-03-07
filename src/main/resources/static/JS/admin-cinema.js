function uploadBanner() {
    let avatar = $('#photo-input-img').prop('files')[0];
    let oFReader = new FileReader();
    oFReader.readAsDataURL(avatar);
    oFReader.onload = function (oFREvent) {
        $("#photo-img").attr('src', oFREvent.target.result);
    };
}

function uploadBanner0() {
    let avatar0 = $('#photo-input-img0').prop('files')[0];
    let oFReader0 = new FileReader();
    oFReader0.readAsDataURL(avatar0);
    oFReader0.onload = function (oFREvent) {
        $("#photo-img0").attr('src', oFREvent.target.result);
    };
}

function uploadBanner1() {
    let avatar1 = $('#photo-input-img1').prop('files')[0];
    let oFReader1 = new FileReader();
    oFReader1.readAsDataURL(avatar1);
    oFReader1.onload = function (oFREvent) {
        $("#photo-img1").attr('src', oFREvent.target.result);
    };
}

function uploadBanner2() {
    let avatar2 = $('#photo-input-img2').prop('files')[0];
    let oFReader2 = new FileReader();
    oFReader2.readAsDataURL(avatar2);
    oFReader2.onload = function (oFREvent) {
        $("#photo-img2").attr('src', oFREvent.target.result);
    };
}

function uploadBanner3() {
    let avatar3 = $('#photo-input-img3').prop('files')[0];
    let oFReader3 = new FileReader();
    oFReader3.readAsDataURL(avatar3);
    oFReader3.onload = function (oFREvent) {
        $("#photo-img3").attr('src', oFREvent.target.result);
    };
}

function uploadBanner4() {
    let avatar4 = $('#photo-input-img4').prop('files')[0];
    let oFReader4 = new FileReader();
    oFReader4.readAsDataURL(avatar4);
    oFReader4.onload = function (oFREvent) {
        $("#photo-img4").attr('src', oFREvent.target.result);
    };
}

function uploadBanner5() {
    let avatar5 = $('#photo-input-img5').prop('files')[0];
    let oFReader5 = new FileReader();
    oFReader5.readAsDataURL(avatar5);
    oFReader5.onload = function (oFREvent) {
        $("#photo-img5").attr('src', oFREvent.target.result);
    };
}




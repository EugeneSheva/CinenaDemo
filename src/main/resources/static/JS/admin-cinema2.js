document.addEventListener('DOMContentLoaded', function() {
    jQuery(document).ready(function () {
        ImgUpload();
    });

    function ImgUpload() {
        var imgWrap2 = "";
        var imgArray2 = [];

        $('.upload__inputfile2').each(function () {
            $(this).on('change', function (e) {
                imgWrap2 = $(this).closest('.upload__box2').find('.upload__img-wrap2');
                var maxLength = $(this).attr('data-max_length2');

                var files = e.target.files;
                var filesArr2 = Array.prototype.slice.call(files); //смотреть сдесь
                var iterator = 0;
                filesArr2.forEach(function (f, index) {

                    if (!f.type.match('image.*')) {
                        return;
                    }

                    if (imgArray2.length > maxLength) {
                        return false
                    } else {
                        var len = 0;
                        for (var i = 0; i < imgArray2.length; i++) {
                            if (imgArray2[i] !== undefined) {
                                len++;
                            }
                        }
                        if (len > maxLength) {
                            return false;
                        } else {
                            imgArray2.push(f);

                            var reader = new FileReader();
                            reader.onload = function (e) {
                                var html = "<div class='upload__img-box2'><div style='background-image: url(" + e.target.result + ")' data-number='" + $(".upload__img-close2").length + "' data-file='" + f.name + "' class='img-bg2'><div class='upload__img-close2' ></div></div></div>" ;
                                imgWrap2.append(html);
                                iterator++;
                            }
                            reader.readAsDataURL(f);
                        }
                    }
                });
            });
        });

        $('body').on('click', ".upload__img-close2", function (e) {
            var file = $(this).parent().data("file");
            for (var i = 0; i < imgArray2.length; i++) {
                if (imgArray2[i].name === file) {
                    imgArray2.splice(i, 1);
                    break;
                }
            }
            $(this).parent().parent().remove();
        });
    }
});


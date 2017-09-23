$(document).ready(function() {
var hei = window.innerHeight;
// $('.item').height(hei-80 );
// $('.item2').height(hei-126 );
$('.a').click(function() {
  if($(this).find('a').hasClass('arroleft')){
     $('.a').find('h1').css("border-bottom-color", "#2c3e4f");
     $(this).find('h1').css("border-bottom-color","#33485d");
     $('.subContent').slideUp();
     $(this).next(".subContent").slideDown();
     $('.leftContent').find('.arr').removeClass('downArro');
     $('.leftContent').find('.arr').addClass('arroleft');
     $(this).find('a').removeClass('arroleft');
     $(this).find('a').addClass('downArro');
  }
  else{
     $(this).find('h1').css("border-bottom-color","#2c3e4f");
     $('.subContent').slideUp(); 
     $('.leftContent').find('.arr').removeClass('downArro');
     $('.leftContent').find('.arr').addClass('arroleft');
  }
  });
  $('.popButton').click(function(e) {
     $('.popupSection').show();
  }); 
  $('.closez, .minus, .hideOk, .hideOkTwo').click(function(e) {
     $('.popupSection').hide();
  });
});

// $(function() {
//   $('.item').perfectScrollbar();
//   $('.item2').perfectScrollbar();
// });
$('.a').on('click',function(){
    $(this).children(".clickz").addClass("new_color");
    $(this).siblings(".a").children(".clickz").removeClass("new_color");
});

$(".subContent h2 a").click(function(){
  $(this).parent().parent().parent().children(".subContent").children("h2").children("a").removeClass("redWord");
  $(this).addClass("redWord");
  // $(this).removeClass("redWord");
});

// function $(id){ 
//   return document.getElementById(id);
// }
// function getHeight() { 
//   if ($("left_id").offsetHeight>=$("right_id").offsetHeight){
//      $("right_id").style.height=$("left_id").offsetHeight + "px";
//   }
//   else{
//      $("left_id").style.height=$("right_id").offsetHeight + "px";
//   }  
// }
// window.onload = function() {
//   getHeight();
// }
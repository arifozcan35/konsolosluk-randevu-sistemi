package com.arifozcan.consulateapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arifozcan.consulateapplication.databinding.ActivityCountryOptionsBinding
import com.arifozcan.consulateapplication.databinding.ActivityVizeTurleriBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class VizeTurleri : AppCompatActivity() {

    private lateinit var binding : ActivityVizeTurleriBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    // sharedPreferences tanımlama
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVizeTurleriBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage


        // SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Seçilen ülke bilgisini al
        val selectedCountry = sharedPreferences.getString("selectedCountry", "")

        binding.txtVizeTurleri.text = selectedCountry


        /*
        // sayfa başlığı (ülke ismi)
        val selectedCountry2 = intent.getStringExtra("selectedCountry")
        val textView2 = findViewById<TextView>(R.id.txtVizeturleri)
        textView2.text = selectedCountry2

         */

        if(selectedCountry == "Türkiye"){
            binding.txtVizeBilgilendirme.text = "Türkiye Vize Türleri\n" +
                    "Türkiye vize başvurusunda seyahat amacına uygun vize türüne göre işlem yapılması gerekmektedir.\n" +
                    "\n" +
                    "Kısa Süreli Vize\n" +
                    "En fazla 180 gün içinde 90 günü aşmamak kaydı ile verilen vizeler kısa süreli vize kategorisinde değerlendirilmektedir.\n" +
                    "\n" +
                    "Türkiye Turizm Vizesi\n" +
                    "Turistik faaliyetler için Türkiye’ye seyahat edeceklerin başvurması gereken vize türüdür. Türkiye ile karşılıklı vize muafiyeti anlaşması bulunan ülkelerin vatandaşları kendi ülkeleri için belirlenen süreler dahilinde vizeden muaftır. Ülkede, belirlenen sürelerden daha fazla kalacakların turizm vizesi başvurusunda bulunması gerekmektedir.\n" +
                    "\n" +
                    "Ticari faaliyetler, seminer, festival ve konferanslar, spor aktiviteleri, kültürel aktiviteler ve iş görüşmeleri için de turizm vizesi alabilirsiniz. Turizm vizesi vatandaşların isteğine göre tek, çift ya da çok giriş hakkı ile alınabilmektedir.\n" +
                    "\n" +
                    "Türkiye Eğitim Vizesi\n" +
                    "Türkiye’de dil kursuna, stajlara ya da öğrenci değişim programlarına katılmak isteyen vatandaşlar eğitim vizesine başvurmaktadır.\n" +
                    "\n" +
                    "Türkiye Çalışma Vizesi\n" +
                    "Çalışmak için Türkiye’ye gelmek isteyen vatandaşların bu vizeye başvurması gerekmektedir. İkamet izni yerine de geçen çalışma vizesi en fazla 90 günlük alınabilmektedir. Ülkede daha uzun kalmak isteyenlerin kısa süreli ikamet için başvuruda bulunması gerekmektedir.\n" +
                    "\n" +
                    "Türkiye Resmi Görev Vizesi\n" +
                    "Diplomatik kurye olarak atanan ya da resmi görevde bulunanlar Türkiye’ye giriş yapabilmek için resmi görev vizesine başvuru yapmaktadır.\n" +
                    "\n" +
                    "Türkiye Diğer Vizeler\n" +
                    "Belirlenen amaçlar dışında kalan arkeolojik kazılar, film ve belgesel çekimleri, aile birleşimi, tıbbi tedavi gibi nedenlerle Türkiye’ye seyahat edeceklerin başvurması gereken vize türüdür.\n" +
                    "\n" +
                    "Türkiye Transit Vizesi\n" +
                    "Türkiye’nin sınır kapılarından geçerek 3. bir ülkeye seyahat edecek vatandaşlar transit vize almaktadır.\n" +
                    "\n" +
                    "Türkiye Havalimanı Transit Vizesi\n" +
                    "Ülkeye giriş yapmadan yalnızca havalimanından 3. bir ülkeye geçecek olan vatandaşların havalimanı transit vizesi alması gerekmektedir.\n" +
                    "\n" +
                    "Kısa Süreli İkamet\n" +
                    "Kısa süreli vizeler, seyahat amacı fark etmeksizin 180 gün içinde 90 günü aşmamak kaydıyla alınabilmektedir. Bu süreden daha uzun süre Türkiye’de kalmak isteyenlerin İl Göç İdaresi Müdürlüklerine başvuruda bulunarak Kısa Süreli İkamet izni alması gerekmektedir. Bu sayede, vatandaşların ülkede kalış süresi uzamaktadır.\n" +
                    "\n" +
                    "Son 1 yıl içinde 120 günden daha fazla ülke dışında kalan vatandaşların ikamet izinleri iptal olmaktadır.\n" +
                    "\n" +
                    "Türkiye Vize Başvuru Şartları Nelerdir?\n" +
                    "Pasaport süresinin seyahat başlangıç tarihi itibari ile en az 6 ay daha geçerliliğinin olması gerekmektedir.\n" +
                    "Türkiye vize başvuru formu İngilizce dilinde doldurulabilmektedir.\n" +
                    "Online vize başvurusunda bulunacaklar Türkçe, İngilizce, Lehçe, Fransızca, İspanyolca ya da Almanca dil opsiyonlarından yararlanabilmektedir.\n" +
                    "Vize başvurusunun en az 1 ay önceden yapılması tavsiye edilmektedir.\n" +
                    "Olumlu sonuçlanan vizeler Türkiye’ye mutlak giriş hakkı tanımamaktadır.\n" +
                    "Konsolosluktan alınan vizelerin en geç 6 ay içinde kullanılması gerekmektedir.\n" +
                    "Vize başvurusunda bulunanların seyahat sürelerini kapsayan seyahat sağlık sigortası yaptırması zorunludur.\n" +
                    "Tüm ülkelerin vatandaşları Türkiye’de 180 gün içinde 90 günü aşmayacak şekilde konaklayabilir.\n" +
                    "Türkiye Vize Başvurusu İçin Gerekli Belgeler\n" +
                    "Pasaport (En fazla 10 yıllık olması gereken pasaportların seyahat dönüş tarihinden itibaren en az 2 ay daha geçerli olması gerekmektedir.)\n" +
                    "1 adet biyometrik fotoğraf (5X6 cm olması gereken fotoğraflar beyaz fon önünde çektirilmelidir.)\n" +
                    "Seyahat sağlık sigortası poliçesi\n" +
                    "Nüfus cüzdanı fotokopisi\n" +
                    "Seyahat amacını, süresini ve finansal durumu belirten dilekçe\n" +
                    "Ulaşım ve konaklama bilgileri (Gidiş/dönüş uçak bileti ve otel rezervasyonlarının onaylanmış olması gerekmektedir.)\n" +
                    "Varsa davet mektubu\n" +
                    "Banka hesap hareketleri\n" +
                    "Güncel faaliyet belgesi (İşverenler için geçerli)\n" +
                    "Finansal durumu destekleyen tapu ve kira kontratı, maaş bordrosu, kredi kartı ve araç ruhsatı bilgileri\n" +
                    "Harç ücretinin yatırıldığını belirten dekont "
        }

        else if(selectedCountry == "Portekiz") {
            binding.txtVizeBilgilendirme.text = "Portekiz Vize Türleri\n" +
                    "Portekiz vize çeşitleri, seyahat süresine ve amacına göre farklılık gösterse de temel olarak, kısa süreli (Schengen vizesi) ve uzun süreli vize olmak üzere iki kategoriye ayrılır:\n" +
                    "\n" +
                    "C Tipi Kısa Süreli Vize\n" +
                    "Kısa süreli vize; bir yükseköğretim kurumunda okumak, bir araştırma projesi yürütmek veya turistik bir geziye katılmak amacıyla ülkede 180 günlük süre içinde yalnızca 90 günden az kalmayı planlayan Türk vatandaşları için geçerlidir.\n" +
                    "\n" +
                    "Turistik Vize\n" +
                    "Portekiz'e yapılacak turistik bir seyahat için verilen bu vize, çoğunlukla 90 gün için geçerlidir.\n" +
                    "\n" +
                    "Ticari Vize\n" +
                    "İş toplantılarına katılmak ya da işle ilgili diğer faaliyetler için Portekiz'e seyahat eden kişilerin ticari vize alması gerekir. Bu vize de genellikle 90 gün için geçerlidir.\n" +
                    "\n" +
                    "Kısa Süreli Eğitim Vizesi\n" +
                    "Staj ya da kurs gibi kısa süreli olarak bir kurumda eğitim almak isteyenlere 90 gün için geçerli kısa süreli eğitim vizesi verilir.\n" +
                    "\n" +
                    "Aile veya Arkadaş Ziyareti Vizesi\n" +
                    "Aile bireyleri ve arkadaşları ziyaret etmek amacıyla alınan bu vize için davet mektubu gerekir.\n" +
                    "\n" +
                    "Kültürel ve Sportif Faaliyetler veya Konferanslar Vizesi\n" +
                    "Bir konferansa, spor etkinliğine ya da kültürel faaliyete katılmak isteyenler bu vizeye başvurur.\n" +
                    "\n" +
                    "Schengen Vizesi Nedir?\n" +
                    "Schengen vizesi; Schengen Bölgesi’nde bulunan 27 ülke arasında yapılan, her ülkenin vatandaşlarının diğer Schengen ülkelerine pasaportsuz seyahat etmesine olanak tanıyan siyasi bir anlaşmanın ürünüdür.\n" +
                    "\n" +
                    "Portekiz de Schengen Bölgesi’nin bir parçasıdır. Bu vize ile yabancı uyruklu kişiler herhangi bir Schengen Bölgesi ülkesine turizm veya iş amacıyla 180 günlük süre içinde 90 güne kadar giriş yapabilir.\n" +
                    "\n" +
                    "Türk vatandaşı olarak Portekiz dahil olmak üzere diğer Schengen ülkelerine turizm veya iş amaçlı seyahat etmek için Schengen turist vizesine başvurmanız gerekir. Schengen vizesine başvurmanın faydaları, vizenin geçerlilik süresi boyunca diğer Schengen Bölgesi ülkelerini de ziyaret edebilmenizdir.\n" +
                    "\n" +
                    "D Tipi Uzun Süreli Vize\n" +
                    "Uzun süreli vize; öğrenim görmek veya çalışmak amacıyla 90 günden daha uzun süre kalmayı planlayan Türk vatandaşları için geçerlidir. Yurt dışında bir yıllık eğitim programına katılmayı veya uzun süreli çalışmayı düşünenler, bu vizeye başvurmalıdır. Bu vize, genellikle bir yıl geçerlidir ancak 5 yıla kadar uzatılabilir.\n" +
                    "\n" +
                    "Uzun süreli vize ile Portekiz’e giriş yapan Türk vatandaşlarının, ülkeye vardıktan sonraki 4 ay içerisinde Portekiz Göçmenlik ve Sınır Dairesi'nden (SEF) oturma izni alması gerekir.\n" +
                    "\n" +
                    "Transit Vize\n" +
                    "Transit vize, Schengen bölgesi ülkesinden gelmeyen vatandaşların Schengen dışındaki başka bir ülkeye giderken uçuşlarını ya da ulaşım şeklini değiştirmek için bir Schengen havalimanında konaklarken başvurmaları gereken izindir. T.C vatandaşları, iki farklı transit vize için başvuru yapabilir:\n" +
                    "\n" +
                    "Havalimanı transit Schengen vizesi\n" +
                    "Denizciler için Schengen vizesi\n" +
                    "Portekiz Vize Başvurusu İçin İstenen Belgeler\n" +
                    "Portekiz seyahati için vize başvurusunda bulunacaklardan istenen belgeler aşağıdaki gibidir:\n" +
                    "\n" +
                    "Seyahat bitiş tarihinden itibaren en az 3 ay daha geçerli olacak, en az 2 sayfası boş olan bir pasaport\n" +
                    "Tam vukuatlı nüfus kayıt örneği\n" +
                    "Nüfus cüzdanı fotokopisi\n" +
                    "Portekiz Büyükelçiliği’nin resmî web sitesinden alınıp doldurulan ve imzalanan vize başvuru formu\n" +
                    "Son 6 ay içinde çekilmiş, beyaz arka planlı, yüzün net göründüğü, başka bir belgeye yapıştırılabilen biyometrik fotoğraf\n" +
                    "Seyahat süresi boyunca geçerli olan seyahat sağlık sigortası\n" +
                    "Seyahat planı ile varış ve dönüş tarihlerini içeren uçak bileti ya da rezervasyon belgesi\n" +
                    "Portekiz'de konaklama merkezini ve tarihleri kanıtlayan bir belge, otel rezervasyonu ya da davet mektubu\n" +
                    "Seyahat masraflarının karşılanacağını belirten ve finansal durumu kanıtlayan banka hesap özetleri, maaş bordrosu gibi belgeler\n" +
                    "Daha önce alınan Schengen vizelerinin fotokopileri\n" +
                    "Bazı durumlarda Portekiz vatandaşı ya da oturum izni olan bir kişiden davetiye mektubu\n" +
                    "Ek olarak; aşağıdaki durumlara göre farklı belgeler istenebilir:\n" +
                    "\n" +
                    "Çalışanlar\n" +
                    "Çalışma belgesi\n" +
                    "İzin belgesi\n" +
                    "SGK hizmet dökümü\n" +
                    "Son 3 aya ait maaş bordroları\n" +
                    "İşe giriş belgesi\n" +
                    "Kamu kuruluşlarında çalışanlardan görev kimlik kartı\n" +
                    "Öğrenciler\n" +
                    "Öğrenci belgesi\n" +
                    "Varsa Portekiz’de bulunan eğitim kurumunda alınan kabul\n" +
                    "18 yaşın altındaki öğrencilerden noter onaylı muvafakatname\n" +
                    "Emekliler\n" +
                    "Emeklilik kartı\n" +
                    "Emeklilik belgesi\n" +
                    "Son 3 aya ait emeklilik maaşının yattığını gösteren hesap dökümü\n" +
                    "Şirket Sahipleri\n" +
                    "Şirkete ait ticari sicil numarası\n" +
                    "İmza sirküleri\n" +
                    "Vergi levhası\n" +
                    "Faaliyet belgesi\n" +
                    "Şirket hesabına ait banka dökümleri\n" +
                    "Portekiz Vize Başvurusu Şartları Nelerdir?\n" +
                    "C vatandaşı olmak\n" +
                    "Başvuru sırasında Türkiye’ye dönme sebeplerini ve bağlayıcılığı kanıtlamak\n" +
                    "Seyahati finanse edebilecek mali gelirleri beyan etmek\n" +
                    "Başvuru formunu eksiksiz ve doğru doldurmak\n" +
                    "5 yıl içinde yapılmamış ise; biyometrik veri işlemleri için parmak izi vermek\n" +
                    "Tüm belgelerin yeminli noterle İngilizce ya da Portekizceye çevrilmiş formlarını sunmak\n" +
                    "Portekiz Vize Başvuru Süreci\n" +
                    "Başvuru formu doldurma: Portekiz Konsolosluğu’nun veya elçiliğinin resmî web sitesinden vize başvuru formunun doldurulması gerekir.\n" +
                    "Gerekli belgelerin hazırlanması: Pasaport, biyometrik fotoğraf, seyahat sigortası, seyahat planı, finansal belgeler ve davetiye gibi tüm dokümanlar; belirtilen şekilde hazırlanmalıdır.\n" +
                    "Randevu alma: Vize başvurusu için randevu alınması gerekir. Randevu sistemi konsolosluğun veya elçiliğin web sitesinde bulunabilir.\n" +
                    "Başvuru ücretinin ödenmesi: Vize başvurusu için belirli bir ücret ödenmesi gerekir. Bu ücret, vizenin süresine ve türüne veya seyahat edecek kişilerin yaşlarına bağlı olarak değişebilir.\n" +
                    "Görüşmeye gidiş: Vize başvuru sürecinde görüşmeye katılmak gerekebilir. Görüşme sırasında başvuru belgelerinin ve seyahat planının sunulması istenir.\n" +
                    "Vize işleminin tamamlanması: Başvurular incelendikten ve onaylandıktan sonra vize verilir.\n" +
                    "Sponsor Mektubu Nedir?\n" +
                    "Sponsor mektubu; bir kişinin ya da kuruluşun, vize başvurusunda bulunan kişinin seyahatini desteklemek amacıyla yazdığı resmî bir yazıdır. Başvuru sahibinin finansal durumunu kanıtlamak ve seyahat maliyetlerini karşılamak için kullanılabilir. Bu belge, özellikle resmi bir geliri olmayanlar tarafından kullanılır.\n" +
                    "\n" +
                    "Başvuru Ücreti Ne Kadar?\n" +
                    "Portekiz’e seyahat edecekler için Schengen vize ücreti 80 euro; 6-12 yaş aralığındaki çocuklar için vize başvurusu ücreti 40 euro’dur. 6 yaşından küçük çocuklar ise vize ücretinden muaf tutulur. Ayrıca Avrupa Birliği vatandaşı olan kişilerin hem 18 yaşından küçük çocukları hem de eşleri vize ücretinden muaftır.\n" +
                    "\n" +
                    "İş ya da eğitim gibi nedenlerle 90 günden fazla süre ile Portekiz’e seyahat edecek kişilerin ulusal vize almaları gerekir. 1 seneden uzun seyahatler için vize ücreti 90 euro’dur. 90 günden uzun ancak 1 seneden az süre ile seyahat edecekler, 75 euro vize ücreti ödemekle yükümlüdür.\n" +
                    "\n" +
                    "Başvuru Merkezleri Nerededir?\n" +
                    "Portekiz vize başvuruları, şahsen Portekiz Büyükelçiliği ya da Macaristan Konsolosluğu’na yapılabilir. Büyükelçilik, aracı kurumlar ile yapılan başvuruları kabul etmemektedir. D tipi uzun süreli vizelerde başvuruların Ankara Büyükelçiliği’ne yapılması zorunludur.\n" +
                    "\n" +
                    "Portekiz Büyükelçiliği adresi: Kırlangıç Sokak No. 39, Gaziosmanpaşa, 06700 Çankaya/Ankara\n" +
                    "İstanbul ve çevresinde yaşayan kişiler C tipi kısa süreli Portekiz vize başvurularını Macaristan Konsolosluğu’na da yapabilir.\n" +
                    "\n" +
                    "Macaristan Konsolosluğu adresi: Polat Ofis, B Blok, İmrahor Caddesi, Yankı Sokak No:27 Gürsel Mahallesi 34400 Kağıthane/İstanbul\n" +
                    "Randevu Nasıl Alınır?\n" +
                    "Vize başvuru randevuları, Portekiz Büyükelçiliği’nin resmî web sitesi üzerinden yalnızca e-mail aracılığı ile alınabilir. Başvuru sahipleri; sitede belirtilen mail adresine kimlik bilgilerini, Portekiz’e seyahat nedenini, gidiş-dönüş tarihlerini ve randevunun kaç kişi için alınacağını içeren bir mail atmalıdır.  Başvuruların, seyahat başlama tarihinden en az 15 gün önce yapılması gerekir.\n" +
                    "\n" +
                    "Schengen vizesine başvuru süreci, genellikle iki hafta sürer ancak başvuruda herhangi bir sorun olması veya yoğun bir dönemde başvurulması durumunda ya da resmî tatiller nedeniyle süreç 30 güne kadar uzayabilir.\n" +
                    "\n" +
                    "Vize Takibi Nasıl Yapılır?\n" +
                    "Vize takipleri, Portekiz Büyükelçiliği aracılığı ile yapılabilir. Başvuru sahipleri, randevuları sırasında konsoloslukta kargo formunu doldurarak pasaportlarının adreslerini iletilmesini sağlayabilir.\n" +
                    "\n" +
                    "Vize Sonucu Kaç Günde Çıkar?\n" +
                    "Vize sonuçları, başvurduğunuz vize tipine ve belgelerinizin eksiksiz olmasına göre değişkenlik gösterebilir. Örneğin 90 günden uzun, 1 yıldan az sürecek bir vizeye başvurduysanız vizenizin sonuçlanması 2 hafta ila 1 ay sürebilir."
        }

        else if(selectedCountry == "Filistin") {
            binding.txtVizeBilgilendirme.textAlignment = View.TEXT_ALIGNMENT_CENTER
            binding.txtVizeBilgilendirme.textSize = 60F
            binding.txtVizeBilgilendirme.text = "FREE PALASTINE !"


        }

        else if(selectedCountry == "Bosna Hersek") {
            binding.txtVizeBilgilendirme.text = "Bosna Hersek Vizesi\n" +
                    "Bosna Hersek, 180 gün içerisinde 90 gün ile sınırlı seyahat edecek T.C. vatandaşlarından vize istememektedir. Uçak bileti ve otel rezervasyon belgelerinin yanı sıra teminat amaçlı belirli bir miktar para yatırılmasını isteyen Bosna Hersek, Türki Cumhuriyetler de dahil olmak üzere dünya üzerindeki 48 ülkeye aynı prosedürü uygulamaktadır. Türki Cumhuriyetler arasından sadece Kazakistan’daki diplomatik, hususi ve hizmet pasaportuna sahip olan vatandaşlar Bosna Hersek vizesinden muaf tutulmaktadır.\n" +
                    "\n" +
                    "Bosna Hersek Avrupa Birliği’ne üye olan ülkeler dışında; Arnavutluk, Andora, Antigua ve Barbuda, Arjantin, Azerbaycan, Avustralya, Bahamalar, Bahreyn, Barbados, Brezilya, Brunei, Kanada, Şili, Kostarika, El Salvador, Guatemala, Honduras, Hong Kong, İsrail, Japonya, Kuveyt, Makao, Kuzey Makedonya, Malezya, Mauritus, Meksika, Monako, Karadağ, Yeni Zellanda, Nikaragua, Umman, Panama, Paraguay, Katar, Rusya, Saint Kitts ve Nevis, San Marino, Sırbistan, Seyşeller, Singapur, Güney Kore, Tayvan, Türkiye, Birleşik Arap Emirlikleri, Amerika Birleşik Devletleri Uruguay ve Venezuela vatandaşlarını vizeden muaf tutmaktadır.\n" +
                    "\n" +
                    "Bosna Hersek Vize Türleri\n" +
                    "Bosna Hersek seyahati için birçok ülke vatandaşının turistik vize almasına gerek yoktur. Bu ülkelerin dışında kalanlardan ve Bosna Hersek’i ticari olarak ziyaret edenlerden vize istenmektedir.\n" +
                    "\n" +
                    "Bosna Hersek Turistik Vizesi\n" +
                    "T.C. vatandaşları, Bosna Hersek’e yapacağı 180 günde 90 günü aşmayacak seyahatlerinde turistik vizeden muaftır. Çin, Mısır, Küba, Kazakistan, Ürdün, Endonezya, Moldova, Pakistan ve Suudi Arabistan’ın bordo pasaportu, Cezayir’in bordo, hususi ve hizmet pasaportu olan vatandaşlarından turistik vize istenmektedir.\n" +
                    "\n" +
                    "Turistik vize için gerekli olan belgeler arasında; pasaport fotokopisi, nüfus cüzdanı fotokopisi, davet mektubu, düzenli aylık gelir belgesi/güvence ya da davet eden kişinin gelir beyanı, seyahat belgesi (uçak ve konaklama) bulunmaktadır.\n" +
                    "\n" +
                    "Bosna Hersek Ticari Vizeler ile İş ve Fuar Ziyareti Vizeleri\n" +
                    "\n" +
                    "Bosna Hersek, uzun süreli ticaret ya da iş yapmak amacıyla ülkeye gelenlerden ticari vize istemektedir. Gerekli bazı evrakların yanı sıra ticaret yapılacak kurumdan ya da işletmeden davetiye mektubu şartı da aranmaktadır.\n" +
                    "\n" +
                    "D Tipi (Uzun Süreli) Vize\n" +
                    "Bosna Hersek’te uzun süre kalacakların ya da öğrencilerin başvurması gereken vize türüdür. Oturum ya da çalışma izni için gerekli olan bu vize türünde tamamlanması zorunlu bazı evraklar bulunmaktadır.\n" +
                    "\n" +
                    "Bosna Hersek Vize Başvuru Şartları Nelerdir?\n" +
                    "Bosna Hersek ticari vizede sıkıntı yaşamamak için randevunun, seyahat tarihinden en erken 1 ay önceye alınması uygundur.\n" +
                    "Ticari vize için istenilen evraklar tamamlandıktan sonra konsolosluğa şahsi olarak ya da yetkili vize merkezleri aracılığıyla başvuru yapılmalıdır.\n" +
                    "Vizeye başvurulacak pasaportun başvuru tarihinden sonra en az 6 ay geçerlilik süresi bulunmalıdır.\n" +
                    "Bosna Hersek Ticari Vize Başvurusu İçin Gerekli Belgeler\n" +
                    "Bosna Hersek için ticari vize alacakların aşağıdaki belgeleri tamamladıktan sonra konsolosluğa başvuruda bulunması gerekmektedir.\n" +
                    "\n" +
                    "Pasaport (Pasaportun yıpranmamış ya da yırtık olmaması, vize için boş sayfaları bulunması gerekmektedir)\n" +
                    "2 adet biyometrik fotoğraf (3,5x4,5 cm ölçülerinde, beyaz fon ve son 3 ay içerisinde çekilmiş)\n" +
                    "Nüfus cüzdanı fotokopisi\n" +
                    "Başvuru formu\n" +
                    "Dilekçe\n" +
                    "Davetiye mektubu\n" +
                    "Uçak ve otel rezervasyon belgeleri\n" +
                    "Bankada seyahat için yeterli olacak miktarda para\n" +
                    "Banka hesap dökümü\n" +
                    "Şirkete ait ticari sicil gazetesi, faaliyet belgesi ve imza sirküleri\n" +
                    "Sağlık sigortası\n" +
                    "Vize ücretinin ödendiğine dair dekont\n" +
                    " \n" +
                    "Başvuru Merkezleri\n" +
                    "Bosna Hersek Büyükelçiliği\n" +
                    "Adres: Turan Emeksiz Sokak Park Blokları B-Blok No.: 3/9-10 Gazi Osman Paşa/ANKARA\n" +
                    "\n" +
                    "Telefon: +90 312 427 36 02\n" +
                    "\n" +
                    "Yetki Alanı: Ankara ve çevre illeri\n" +
                    "\n" +
                    "İstanbul Başkonsolosluğu\n" +
                    "Adres: Dikilitaş Mah. Yeni Gelin Sok. No.: 24 Kat:3 Beşiktaş/İSTANBUL\n" +
                    "\n" +
                    "Telefon: +90 212 236 6934\n" +
                    "\n" +
                    "Yetki Alanı: İstanbul ve çevre illeri\n" +
                    "\n" +
                    "İzmir Fahri Konsolosluğu\n" +
                    "Adres: Mustafa Kemal Atatürk Bul. No.:65 Atatürk Organize Sanayi Bölgesi 35620 Çiğli/İZMİR\n" +
                    "\n" +
                    "Telefon: +90 232 328 1890\n" +
                    "\n" +
                    "Yetki Alanı: İzmir ve çevre iller\n" +
                    "\n" +
                    "Randevu Nasıl Alınır?\n" +
                    "Bosna Hersek ticari vize randevusu ikamet edilen ilin yetkili başkonsolosluğundan alınmaktadır. Seyahat tarihinden 1 ay önce randevu oluşturulması olası aksaklıkları ortadan kaldırmaktadır.\n" +
                    "\n" +
                    "Vize İşlemleri ve Takibi\n" +
                    "90 günden daha uzun süreli Bosna Hersek’te kalacak ve ticari anlaşmalar yapılacaksa ülkeye vizesiz giriş yapılmaz.\n" +
                    "Ticari vizeye başvurmak ve bu aşamada gerekli olan tüm evrakların toplanması gerekmektedir.\n" +
                    "Konsolosluğun istediği evrakları tamamlamada vize merkezlerinden danışmanlık almak ve evraklar yüzünden vize reddinin önüne geçmek mümkündür.\n" +
                    "Evrakların tamamlanmasının ardından şahsi ya da vize merkezleri aracılığıyla vize randevusu almak gerekmektedir.\n" +
                    "Resmî günler, özel günler vs. dikkat edip vize süreci buna göre yürütülmelidir."
        }

        else if(selectedCountry == "Kazakistan") {
            binding.txtVizeBilgilendirme.text = "Kazakistan Vize Türleri\n" +
                    "T.C. vatandaşları 30 güne kadar olan turistik seyahatlerde vizeden muaf olsa da uzun süreli seyahatler için vize başvurusunda bulunmak gerekmektedir. Seyahatin süresi ve amacına göre vize türleri değişiklik göstermektedir.\n" +
                    "\n" +
                    "Kısa Süreli Vize\n" +
                    "Kazakistan’ın tüm vize türleri en fazla 90 günlük verilmekte olup uzatma işlemleri için yeniden konsolosluğa başvurmak gerekmektedir.\n" +
                    "\n" +
                    "Kazakistan Turistik Vizesi\n" +
                    "90 güne kadar turistik seyahatler için başvurulması gereken vize türüdür. Herhangi bir pasaporta sahip T.C. vatandaşları 30 güne kadar olan turistik gezilerinde vizeden muaftır.\n" +
                    "\n" +
                    "Kazakistan Ticari Vizeler ile İş ve Fuar Ziyareti Vizeleri\n" +
                    "İş görüşmeleri, müşteri ziyaretleri, fuar ve seminerlere katılım gibi nedenlerle ülkeye seyahat edecekler bu vizeye başvurmaktadır. Ticari vizelerde davetiye mektubu şartı aranmaktadır.\n" +
                    "\n" +
                    "Kazakistan Diplomatik Vizesi\n" +
                    "Diplomatik pasaport sahiplerinin harç ücreti ödemeden alabilecekleri vize türüdür.\n" +
                    "\n" +
                    "Kazakistan Yatırımcı Vizesi\n" +
                    "Ülkede yatırım maksatlı ticari faaliyetlerde bulunacak şirket yöneticileri ve çalışanlarının yatırımcı vizesine başvurması gerekmektedir.\n" +
                    "\n" +
                    "Kazakistan Çalışma Vizesi\n" +
                    "Kazakistan’da çalışmasına hukuki açıdan engel olmayan kişiler bu vizeye başvuruda bulunarak ve gerekli belgeleri ibraz ederek ülkede çalışma izni alabilmektedir.\n" +
                    "\n" +
                    "Kazakistan Transit Vizesi\n" +
                    "Kazakistan üzerinden 3. bir ülkeye yapılacak seyahatlerde 5 gün için geçerli olan transit vizenin alınması gerekmektedir.\n" +
                    "\n" +
                    "Kazakistan Vize Başvuru Şartları Nelerdir?\n" +
                    "Kazakça ve İngilizce dil opsiyonları ile sunulan Kazakistan vize başvuru formu doldurulurken İngilizce ya da Türkçe dilleri kullanılabilmektedir. Büyük harfle ve tükenmez kalemle doldurmanız gereken formda ıslak imza bulunması zorunludur.\n" +
                    "Vize türü fark etmeksizin tüm vizeler ülkede 90 güne kadar kalabilmenizi sağlamaktadır. Daha uzun süre kalmak için tekrar konsolosluklara vize başvurusunda bulunmanız gerekmektedir.\n" +
                    "Konsolosluğa ibraz edilen belgelerdeki tüm bilgilerin birbiri ile tutarlı olması önemlidir.\n" +
                    "Eski ve yıpranmış pasaportlar bazı durumlarda kabul edilmemektedir. Bu nedenle, deforme olmuş pasaportların vize başvurusundan önce yenilenmesinde fayda bulunmaktadır.\n" +
                    "Kazakistan Vize Başvurusu İçin Gerekli Belgeler\n" +
                    "Pasaport (Pasaportların seyahat dönüş tarihinden sonra en az 6 ay daha geçerliliğinin olması, mühür basılacak en az 2 sayfasının boş olması ve en fazla 10 yıllık olması gerekmektedir.)\n" +
                    "2 adet biyometrik fotoğraf (Beyaz arka fona sahip olan fotoğrafların 3,5X4,5 cm boyutlarında olması gerekmektedir.)\n" +
                    "Kazakistan vize başvuru formu\n" +
                    "Seyahat sağlık sigortası poliçesi\n" +
                    "Kimlik fotokopisi\n" +
                    "Varsa davet mektubu\n" +
                    "Konaklama ve ulaşım bilgilerinin dökümleri (Ülkede kalınacak otel ve gidiş/dönüş uçak bileti rezervasyon bilgilerini içermektedir.)\n" +
                    "Seyahat amacını ve süresini belirten dilekçe (antetli kağıda yazılmalı)\n" +
                    "Baka hesap dökümü (Seyahat masraflarını karşılayacağınız kadar tutarın görüldüğü banka hesap dökümünün ıslak imzalı ve kaşeli olması gerekmektedir.)\n" +
                    "SGK işe giriş belgesi (Yalnızca sigortalı çalışanlar için geçerli)\n" +
                    "Son 3 aya ait maaş bordrosu (Yalnızca sigortalı çalışanlar için geçerli)\n" +
                    "Şirket faaliyet belgesi ve imza sirküleri (Yalnızca işverenler için geçerli)\n" +
                    "Varsa tapu ve kira kontratları ile araç ruhsatı ve kredi kartı bilgileri \n" +
                    "Başvuru Merkezleri\n" +
                    "Kazakistan Büyükelçiliği\n" +
                    "Adres: Kılıç Ali Sokağı, No: 6, Diplomatik Site, Oran/Ankara\n" +
                    "\n" +
                    "Telefon: +90 312 491 91 00\n" +
                    "\n" +
                    "Görev Bölgeleri: Ankara ve çevresinde bulunan iller\n" +
                    "\n" +
                    "İstanbul Başkonsolosluğu\n" +
                    "Adres: Yeşilyurt Mahallesi, Başak Sokak, No: 35, Florya, Bakırköy/İstanbul\n" +
                    "\n" +
                    "Telefon: +90 212 662 53 47\n" +
                    "\n" +
                    "Görev Bölgeleri: İstanbul ve çevresinde bulunan iller\n" +
                    "\n" +
                    "Randevu Nasıl Alınır?\n" +
                    "30 günden fazla olan seyahatler için Kazakistan vize başvurusunda bulunmak ve konsolosluklardan randevu talebinde bulunmak gerekmektedir. Randevu talebinizi konsoloslukların yetkilendirdiği danışma merkezleri aracılığı ile yapabilirsiniz. Turizm acenteleri ve yetkili danışma merkezleri randevu günü ve saatinde konsolosluklara vize başvuru belgelerini iletmektedir. Bu nedenle, başvuru sahiplerinin randevuya şahsen katılmasına gerek yoktur.\n" +
                    "\n" +
                    "Vize İşlemleri ve Takibi\n" +
                    "Kazakistan’da 30 günden fazla kalacaklar ya da ticari faaliyetlerde bulunacakların öncelikle seyahat amacına ve süresine göre vize türüne karar vermesi gerekmektedir. Başvurular, belirlenen vize türü için geçerli olmaktadır.\n" +
                    "Vize türüne karar verdikten sonra Kazakistan vize başvuru formunu İngilizce ya da Türkçe dillerinde büyük harflerle ve tükenmez kalemle doldurmanız gerekmektedir. Konsolosluk, başvuru belgelerinin eksiksiz ve hatasız doldurulmasını talep etmektedir.\n" +
                    "Harç ücretini yatırmanız ve başvuru belgesini doldurmanızın ardından başvurunuzu gerçekleştirebilirsiniz.\n" +
                    "Vize başvuruları konsoloslukların görevlendirdiği danışma merkezleri aracılığı ile yapılmaktadır. Merkezler, aynı zamanda belgelerin toplanması, sıralanması, randevu alınması ve belgelerin tesliminde de başvuru sahiplerine yardımcı olmaktadır.\n" +
                    "Konsolosluğun belgeleri incelemesinin ardından vize sonucu belli olmaktadır. Konsolosluklar, başvuru sahiplerinden ek belge de talep edebilmektedir."
        }

        else if(selectedCountry == "Fas") {
            binding.txtVizeBilgilendirme.text = "Fas Türkiye’den Vize İstiyor mu?\n" +
                    "Fas; diplomatik, hususi, hizmet ve umuma mahsus pasaportu bulunan T.C. vatandaşlarından vize istememektedir. Vizesiz girilen Fas’ta kalış süresi ise 180 gün içerisinde 90 ile sınırlıdır.\n" +
                    "\n" +
                    "Fas’ın Vize İstemediği Ülkeler Hangileridir?\n" +
                    "Türkiye’nin yanı sıra Cezayir, Andorra, Arjantin, Avustralya, Avusturya, Bahreyn, Belçika, Brezilya, Bulgaristan, Kanada, Şili, Kongo Cumhuriyeti, Hırvatistan, Kıbrıs, Çek Cumhuriyeti, Danimarka, Estonya, Finlandiya, Fransa, Almanya, Büyük Britanya, Yunanistan, Gine, Hong Kong, Macaristan, İzlanda, Endonezya, İrlanda, İtalya, Japonya, Kuveyt, Letonya, Lihtenştayn, Litvanya, Lüksemburg, Mali, Malta, Meksika, Monako, Hollanda, Yeni Zelanda, Nijer, Norveç, Umman, Peru, Filipinler, Polonya, Portekiz, Porto Riko, Katar, Romanya, Suudi Arabistan, Senegal, Singapur, Slovakya, Slovenya, Güney Kore, İspanya, İsveç, İsviçre, Tunus, Birleşik Arap Emirlikleri, Amerika Birleşik Devletleri ve Venezuela da Fas vizesinden muaf ülkeler arasında bulunmaktadır.\n" +
                    "\n" +
                    "Fas Vizesi Alması Gereken Ülkeler\n" +
                    "Umuma mahsus pasaportu bulunan Dominik Cumhuriyeti, Pakistan, Uruguay, Kolombiya, Kosta Rika, Azerbaycan, Karadağ, Sırbistan, Ukrayna, Ürdün, Mısır, Gabon, Moritanya, Sao Tome ve Principe, Sierra Leone, Sudan, Vietnam ve Yemen vatandaşlarının Fas vizesi alması gerekmektedir.\n" +
                    "\n" +
                    "Fas’a Giderken Gerekli Olan Evraklar Nelerdir?\n" +
                    "Fas’ın vize politikasında turistik, ticari, eğitim, çalışma vizeleri bulunmaktadır. T.C. vatandaşları bu vize türlerinden 180 gün içerisinde 90 gün kalmak suretiyle muaf olsalar da ülkeye girerken bazı kurallara uymaları gerekmektedir.\n" +
                    "\n" +
                    "En az 6 ay geçerliliği bulunan pasaport\n" +
                    "Nüfus cüzdanı fotokopisi\n" +
                    "Ulaşım ve konaklama belgeleri\n" +
                    "Seyahat sağlık sigortası\n" +
                    "Ülke giriş çıkışında doldurulması gereken formlar\n" +
                    "Varsa Fas’tan gelen davetiye"
        }

        }

    // option menü seçenekleri
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu2,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.goback) {
            //Go back
            val intent = Intent(applicationContext, CountryOptions::class.java)
            startActivity(intent)
            finish()

        }
        return super.onOptionsItemSelected(item)
    }

}
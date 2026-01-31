import re
from nltk import pos_tag, word_tokenize
from StanfordPOSTagger import StanfordPOSTagger
st = StanfordPOSTagger()

class BaseSentence:
    APICall_re = re.compile(r'(\w+(?:[.]\w+)*(?:\(\w*\)|<\w*>|\[\w*\]))')

    def __init__(self, senetence):
        self.sentence = senetence

    def remove_ing(self, verb):
        verb = verb[:-3]
        if verb[-1]==verb[-2]:
            return verb[:-1] #Getting => Gett => Get
        else:
            return verb
    def make_verb_third_person(self, verb):
        if verb.lower() in ['am', 'are', 'do', 'does']:
            return verb
        if verb[-1] in ['s', 'o', 'a', 'i', 'u']:
            return verb + 'es'
        else:
            return verb + 's'

    def is_verb_following_modal(self, pos, index):
        if index >= 1 and pos[index - 1][0].lower() in ['can', 'could', 'may', 'might', 'shall', 'should', 'will', 'would', 'must']:
            return True
        elif index >= 2 and pos[index - 2][0].lower() in ['ought', 'used'] and pos[index - 1][0].lower() == 'to':
            return True
        elif index >= 2 and pos[index - 1][0].lower() == 'had' and pos[index - 1][0].lower() == 'better':
            return True
        return False

    def custom_tag(self, s):
        allPos = []
        for each in self.APICall_re.split(s):
            if each == "": continue
            if each == None: continue
            if self.APICall_re.match(each):
                p = (each, "NNP") #We consider as proper noun
                allPos.append(p)
            else:
                p = st.tag(word_tokenize(each))
                allPos.extend(p)
        return allPos


    def rephrase(self):
        return 'Override Me'

class HowtoSentence(BaseSentence):
    def __init__(self, senetence):
        BaseSentence.__init__(self, senetence)

    def rephrase(self):
        final = ''
        pos = self.custom_tag(self.sentence)
        for index, t in enumerate(pos):
            if index == 0 or index==1: #'How to'
                continue

            if (t[1]=='VB' or t[1]=='VBP') and self.is_verb_following_modal(pos, index)==False:
                final += self.make_verb_third_person(t[0])+' '
            elif t[1]=='VBG' and pos[index-1][0].lower() in ['and', 'or']:
                final += self.make_verb_third_person(self.remove_ing(t[0]))+ ' '
            else:
                final += t[0]+' '

        final = final.strip()
        if len(final)>0:
            final = final[0].capitalize() + final[1:]
        return final

class HowAuxSentence(BaseSentence):
    def __init__(self, senetence):
        BaseSentence.__init__(self, senetence)
    def rephrase(self):
        final = ''
        pos = self.custom_tag(self.sentence)

        for index, t in enumerate(pos):
            if index == 0 or index==1: # "How" "do" I ...
                continue
            if index == 2 and (t[1] == 'PRP' or t[0].lower() in ['i', 'you', 'he', 'she', 'it', 'we', 'they', 'me', 'him', 'her', 'us', 'them']): # How do "I" ... ##Some times 'i' is considered as 'FW' instead of 'PRP'!!
                continue

            if (t[1]=='VB' or t[1]=='VBP') and self.is_verb_following_modal(pos, index)==False:
                final += self.make_verb_third_person(t[0])+ ' '
            elif t[1]=='VBG' and pos[index-1][0].lower() in ['and', 'or']:
                final += self.make_verb_third_person(self.remove_ing(t[0]))+ ' '
            else:
                final += t[0]+' '

        final = final.strip()
        if len(final) > 0:
            final = final[0].capitalize()+final[1:]
        return final

class OtherSentence(BaseSentence):
    def __init__(self, senetence):
        BaseSentence.__init__(self, senetence)

    def rephrase(self):
        final = ''
        pos = self.custom_tag(self.sentence)
        index_firstVerbOrNounOrDetermined = -1
        for index, t in enumerate(pos):
            if index_firstVerbOrNounOrDetermined==-1 and ((t[1].startswith('VB') and t[0][0]!="'" ) or t[1].startswith('NN') or t[1].startswith('DT')):
                index_firstVerbOrNounOrDetermined = index

            if index_firstVerbOrNounOrDetermined == -1:
                continue
            #################################
            if (t[1]=='VB' or t[1]=='VBP') and self.is_verb_following_modal(pos, index)==False:
                final += self.make_verb_third_person(t[0]) +' '
            elif t[1]=='VBG' and (index==index_firstVerbOrNounOrDetermined or pos[index-1][0].lower() in ['and', 'or']):
                final += self.make_verb_third_person(self.remove_ing(t[0]))+ ' '
            else:
                final += t[0]+' '

        final = final.strip()
        if len(final) > 0:
            final = final[0].capitalize() + final[1:]
        return final


class DescriptionTransformer:
    cleanUp_lp_re = re.compile(r'[(]\s+')
    cleanUp_rp_re = re.compile(r'\s+[)]')
    cleanUp_lq = re.compile(r"``\s+")
    cleanUp_rq = re.compile(r"\s+''")
    cleanUp_Commas_re = re.compile('\s+,')
    ############################### Regex ##############################################
    hasURL_re = re.compile(r'(?:at|in|on|here|@|see|check|check out|look|look at)?\s*[^a-zA-Z0-9_\]})]?\s*https?://\S+\s*[^a-zA-Z0-9_\[{(]?', re.IGNORECASE)
    ###############
    adverbs_pattern = '(?:programmatically|dynamically|through code|by code|using code|in code)'
    adverb_re = re.compile(r'[(\[]?\s*,?\s*%s\s*,?\s*[)\]]?' % (adverbs_pattern), re.IGNORECASE)
    ###############
    floatingPointNumber_pattern = r'\d+(?:[.]\d+)*(?:[.]X|[.][*])*[\b\W]' # Before handling asterisk 4.* : floatingPointNumber_pattern = r'\d+(?:.\d+)*(?:.X|.\*)*\b'
    floatingPointNumber_optioanlParentese_pattern = r'(?:%s|[(\[]\s*%s\s*[)\]])' % (floatingPointNumber_pattern, floatingPointNumber_pattern)
    android_names = r'(?:pre-)?(?:Cupcake|Donut|Eclair|Froyo|Gingerbread|Honeycomb|Ice Cream Sandwich|IceCream Sandwich|ICS\b|Jelly Bean|JB\b|KitKat|Lollipop|android l\b|Marshmallow|android m\b|Nougat|android n\b|android tv\b)'
    suffixWords_pattern = r'(?:beta|versions|version|application|app|devices|device|emulators|emulator|studio)'
    android_fullname_pattern = r"(?:android(?!'s)|android\s*%s|(?:android)?\s*%s)\s*%s?" % (floatingPointNumber_optioanlParentese_pattern, android_names, suffixWords_pattern)
    android_fullname_withOptioanlRange_pattern = android_fullname_pattern + r'(?:\s*\+|\s*(?:\band|\bor|/|&|\bto|-|)\s*(?:higher|above|later|upper|up|further|below|lower|low|(?:android)?\s*(?:%s|%s)))*\s*%s?' % (floatingPointNumber_optioanlParentese_pattern, android_names, suffixWords_pattern)
    prefixWords_pattern = r'(?:^|-|\bin|\bon|\bwith|\bfor|\bfrom|\bor|\band|&)'  # and/or/& for "... under phonegap and android 3.2?"
    android_pattern_g1 = r'[(\[]\s*%s?\s*(?:%s|android)\s*[)\]](?:\s*[|\-:])?' % (prefixWords_pattern, android_fullname_withOptioanlRange_pattern)
    android_pattern_g2 = r'%s\s*%s(?:\s*[|\-:])?' % (prefixWords_pattern, android_fullname_withOptioanlRange_pattern)
    floatingPointNumber_withOptionalRange_pattern = floatingPointNumber_pattern + r'(?:\s*\+|\s*and above|\s*and later|\s*and higher|\s*and below|\s*and lower|\s*and up|\s*and further|\s*(?:-|to|and|or)\s*%s)?' % (floatingPointNumber_pattern)
    api_pattern = r'(?:android)?\s*(?:api|sdk)\s*(?:level)?\s*\:?\s*(?:>|<|>=|<=)?%s\s*%s?' % (floatingPointNumber_withOptionalRange_pattern, suffixWords_pattern)
    api_patter_g1 = r'[(\[]\s*%s?\s*%s\s*[)\]]' % (prefixWords_pattern, api_pattern)  # [in? android? api|sdk level? :? 23+]
    api_patter_g2 = r'%s?\s*%s\s*$' % (prefixWords_pattern, api_pattern)
    api_patter_g3 = r'%s\s*%s' % (prefixWords_pattern, api_pattern)  # from android? api|sdk level? :? 23+
    other_pattern = r'(?:[(\[]\s*%s\s*[)\]]|/java|with java|at runtime|during runtime)' % (suffixWords_pattern)
    java_pattern = r'[(\[]\s*(?:in|for|by|using|with)?\s*java\s*[)\]]'
    androidStuff_re = re.compile(r'\s*(?:' + android_pattern_g1 + '|' + android_pattern_g2 + '|' + java_pattern + '|' + api_patter_g1 + '|' + api_patter_g2 + '|' + api_patter_g3 + '|' + other_pattern + ')\s*,?\s*', re.IGNORECASE)
    ###############
    spaces_re = re.compile(r'\s+')
    words_re = re.compile(r'\S+', re.IGNORECASE)
    ###############
    findBadHow_step1_re = re.compile(r'\bhow-to\b', re.IGNORECASE)
    findBadHow_step2_re = re.compile(r'\bhowto\b', re.IGNORECASE)
    findBadHow_step3_re = re.compile(r'\bhow to:\b', re.IGNORECASE)
    ###############
    hasHow_re = re.compile(r'\bhow ', re.IGNORECASE)
    extractingHowToQuestion_re = re.compile(r'\bhow to\b.*?(?=[?!]|$|[.]$|[.]\W| -|- |\bhow (?!many|much)\b)', re.IGNORECASE)
    extractingHowAuxQuestion_re = re.compile(r'\bhow\b(?! to).*?(?=[?!]|$|[.]$|[.]\W| -|- |\bhow (?!many|much)\b)', re.IGNORECASE)
    ###############
    sentenceSplitter_re = re.compile(r'[.:!?]+\W+')
    ###############
    ############################### Regex ##############################################

    ###


    def __init__(self):
        self.initial()


    def initial(self):
        self.description = ''
        self.refinedDescription = ''
        self.__sentences = []
        self.__resultDescription = ''

    def __str__(self):
        return self.description

    def setDescription(self, description):
        self.initial()
        self.description = description
        self.clean()

    def unescape_html_tags(self, text):
        text = text.replace('&#039;', "'")
        text = text.replace('&quot;', '"')
        text = text.replace('&gt;', '>')
        text = text.replace('&lt;', '<')
        text = text.replace('&amp;', '&')

        return text

    def clean(self):
        self.refinedDescription = self.description
        self.refinedDescription = self.refinedDescription.replace('\n', ' ')
        self.refinedDescription = self.unescape_html_tags(self.refinedDescription)
        self.refinedDescription = self.hasURL_re.sub(' ', self.refinedDescription)
        if 'how' in self.refinedDescription.lower():
            self.refinedDescription = self.findBadHow_step1_re.sub('how to', self.refinedDescription)
            self.refinedDescription = self.findBadHow_step2_re.sub('how to', self.refinedDescription)
            self.refinedDescription = self.findBadHow_step3_re.sub('how to', self.refinedDescription)

    def remove_noises(self):
        self.refinedDescription = self.adverb_re.sub(' ', self.refinedDescription)
        self.refinedDescription = self.androidStuff_re.sub(' ', self.refinedDescription)
        ### Clean up extra spaces
        self.refinedDescription = self.spaces_re.sub(' ', self.refinedDescription)
        self.refinedDescription = self.refinedDescription.strip()


    def getTransformedSenetence(self):
        for eachSen in self.sentenceSplitter_re.split(self.refinedDescription):

            if self.is_sentence_ok(eachSen):
                ## Acceptable Sentence

                if eachSen[-1] in ['.', '!', '?']:
                    transformedSentence = self.transform_sentence(eachSen[:-1])
                else:
                    transformedSentence = self.transform_sentence(eachSen)
                if transformedSentence!=None and transformedSentence!="":
                    #Good to go
                    self.__sentences.append(transformedSentence)
                    pass
        #######

        if len(self.__sentences)>0:
            for each in self.__sentences:
                self.__resultDescription += each+'. '
            self.make_pertty_result()
        else:
            self.__resultDescription = None

        return self.__resultDescription

    def make_pertty_result(self):

        self.__resultDescription = self.cleanUp_lp_re.sub('(', self.__resultDescription)
        self.__resultDescription = self.cleanUp_rp_re.sub(')', self.__resultDescription)
        self.__resultDescription = self.cleanUp_lq.sub('"', self.__resultDescription)
        self.__resultDescription = self.cleanUp_rq.sub('"', self.__resultDescription)
        self.__resultDescription = self.cleanUp_Commas_re.sub(',', self.__resultDescription)

    def transform_sentence(self, sentence):

        l = self.extractingHowToQuestion_re.findall(sentence)
        if len(l) > 0:
            return HowtoSentence(l[0]).rephrase()

        l = self.extractingHowAuxQuestion_re.findall(sentence)
        if len(l) > 0:
            return HowAuxSentence(l[0]).rephrase()


        return OtherSentence(sentence).rephrase()

    def is_sentence_ok(self, sentence):

        words = self.words_re.findall(sentence)
        hasHow = len(self.hasHow_re.findall(sentence)) > 0

        if len(words)< 4:
            return False
        elif hasHow:
            return True
        else:
            tags = pos_tag(word_tokenize(sentence))
            for (word, tag) in tags:
                if tag.startswith('VB'):
                    return True
            return False

if __name__ == "__main__":
    pass
    dt = DescriptionTransformer()

    samples = ["How to pass an object from one activity to another on Android", "Programmatically download a file with Android, and showing the progress in a ProgressDialog", "A simple wrapper for Scalpel (https://github.com/JakeWharton/scalpel) that includes toggle controls accessible from a right-side navigation drawer."]
    for eachSample in samples:
        print "BEFORE:\t\t",eachSample
        dt.setDescription(eachSample)
        dt.remove_noises()
        tarnsformed = dt.getTransformedSenetence()
        print "AFTER:\t\t", tarnsformed
        print "-----"

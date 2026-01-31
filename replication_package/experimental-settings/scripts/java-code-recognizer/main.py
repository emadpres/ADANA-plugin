import sampleData
import re

class JavaCodeDetector:
    java_re = re.compile(r'^\s*@Override', re.MULTILINE)
    mk_re = re.compile(r'^\s*\w+\s+:=', re.MULTILINE)
    cpp_re = re.compile(r'^\s*#ifdef|^\s*#ifndef|^\s*#include|^\s*#define|^\s*extern "C"|^\s*public:|^\s*private:|^\s*protected:', re.MULTILINE)

    def is_java(self, code):

        if len(self.java_re.findall(code))>0:
            return True

        if code.startswith('<') or code.endswith('>'):   #It's XML
            return False

        if len(self.mk_re.findall(code))>0:           #It's Android.mk
            return False

        if len(self.cpp_re.findall(code)) > 0:  # It's C++
            return False
        return True



if __name__ == "__main__":
    ##########################
    sample_java_code, sample_cpp_code, sample_xml, sample_mk = sampleData.load_sample_data()
    ##########################
    j = JavaCodeDetector()
    print "Java:",j.is_java(sample_java_code);
    print "C++:", j.is_java(sample_cpp_code);
    print "XML:", j.is_java(sample_xml);
    print "MK:", j.is_java(sample_mk);

#!/usr/bin/perl

use Data::Dumper;
use JSON;
use File::Slurp;

$raw_json = read_file('template.data');
$template_json = decode_json $raw_json;

for(<>) {
    chomp;
    ($output,$input,$material) = split /::/;
    $template_json->{"conditions"}[0]->{"values"}[0]->{"optionValue"} = $material;
    $template_json->{"key"}->{"x"}->{"ore"} = $input;
    $template_json->{"result"}->{"item"} = $output;

    $out = JSON->new->utf8->space_after->space_before(false)->indent->encode($template_json);
    open($f, '>', lc($material) . "_rod.json");
    print $f "$out\n";
    close $f;
}

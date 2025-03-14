package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum EventType {
    @XmlEnumValue("not existing")
    NOT_EXISTING("not existing"),
    @XmlEnumValue("not reported")
    NOT_REPORTED("not reported"),
    @XmlEnumValue("earthquake")
    EARTHQUAKE("earthquake"),
    @XmlEnumValue("anthropogenic event")
    ANTHROPOGENIC_EVENT("anthropogenic event"),
    @XmlEnumValue("collapse")
    COLLAPSE("collapse"),
    @XmlEnumValue("cavity collapse")
    CAVITY_COLLAPSE("cavity collapse"),
    @XmlEnumValue("mine collapse")
    MINE_COLLAPSE("mine collapse"),
    @XmlEnumValue("building collapse")
    BUILDING_COLLAPSE("building collapse"),
    @XmlEnumValue("explosion")
    EXPLOSION("explosion"),
    @XmlEnumValue("accidental explosion")
    ACCIDENTAL_EXPLOSION("accidental explosion"),
    @XmlEnumValue("chemical explosion")
    CHEMICAL_EXPLOSION("chemical explosion"),
    @XmlEnumValue("controlled explosion")
    CONTROLLED_EXPLOSION("controlled explosion"),
    @XmlEnumValue("experimental explosion")
    EXPERIMENTAL_EXPLOSION("experimental explosion"),
    @XmlEnumValue("industrial explosion")
    INDUSTRIAL_EXPLOSION("industrial explosion"),
    @XmlEnumValue("mining explosion")
    MINING_EXPLOSION("mining explosion"),
    @XmlEnumValue("quarry blast")
    QUARRY_BLAST("quarry blast"),
    @XmlEnumValue("road cut")
    ROAD_CUT("road cut"),
    @XmlEnumValue("blasting levee")
    BLASTING_LEVEE("blasting levee"),
    @XmlEnumValue("nuclear explosion")
    NUCLEAR_EXPLOSION("nuclear explosion"),
    @XmlEnumValue("induced or triggered event")
    INDUCED_OR_TRIGGERED_EVENT("induced or triggered event"),
    @XmlEnumValue("rock burst")
    ROCK_BURST("rock burst"),
    @XmlEnumValue("reservoir loading")
    RESERVOIR_LOADING("reservoir loading"),
    @XmlEnumValue("fluid injection")
    FLUID_INJECTION("fluid injection"),
    @XmlEnumValue("fluid extraction")
    FLUID_EXTRACTION("fluid extraction"),
    @XmlEnumValue("crash")
    CRASH("crash"),
    @XmlEnumValue("plane crash")
    PLANE_CRASH("plane crash"),
    @XmlEnumValue("train crash")
    TRAIN_CRASH("train crash"),
    @XmlEnumValue("boat crash")
    BOAT_CRASH("boat crash"),
    @XmlEnumValue("other event")
    OTHER_EVENT("other event"),
    @XmlEnumValue("atmospheric event")
    ATMOSPHERIC_EVENT("atmospheric event"),
    @XmlEnumValue("sonic boom")
    SONIC_BOOM("sonic boom"),
    @XmlEnumValue("sonic blast")
    SONIC_BLAST("sonic blast"),
    @XmlEnumValue("acoustic noise")
    ACOUSTIC_NOISE("acoustic noise"),
    @XmlEnumValue("thunder")
    THUNDER("thunder"),
    @XmlEnumValue("avalanche")
    AVALANCHE("avalanche"),
    @XmlEnumValue("snow avalanche")
    SNOW_AVALANCHE("snow avalanche"),
    @XmlEnumValue("debris avalanche")
    DEBRIS_AVALANCHE("debris avalanche"),
    @XmlEnumValue("hydroacoustic event")
    HYDROACOUSTIC_EVENT("hydroacoustic event"),
    @XmlEnumValue("ice quake")
    ICE_QUAKE("ice quake"),
    @XmlEnumValue("slide")
    SLIDE("slide"),
    @XmlEnumValue("landslide")
    LANDSLIDE("landslide"),
    @XmlEnumValue("rockslide")
    ROCKSLIDE("rockslide"),
    @XmlEnumValue("meteorite")
    METEORITE("meteorite"),
    @XmlEnumValue("volcanic eruption")
    VOLCANIC_ERUPTION("volcanic eruption");

    private final String value;

    EventType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return this.value();
    }

    public static EventType fromValue(String v) {
        for (EventType c: EventType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return null;
    }

}
